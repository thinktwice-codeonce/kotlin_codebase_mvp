package com.codebase.mvp.service.network.provider

import android.content.Context
import com.codebase.mvp.service.common.RestMessageResponse
import com.codebase.mvp.service.converter.ThreeTenGsonAdapter
import com.codebase.mvp.service.network.filter.*
import com.codebase.mvp.service.network.intercepter.HttpLoggingInterceptor
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.greenrobot.eventbus.EventBus
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class DefaultNetworkProvider(context: Context, private val isDebug: Boolean) : BaseNetworkProvider(context) {

    companion object {
        val PROGRESS_BUS = EventBus()
    }

    val headers: MutableMap<String, String> = HashMap()
    val interceptors: MutableList<Interceptor> = ArrayList()
    val networkInterceptors: MutableList<Interceptor> = ArrayList()
    val filterChain: FilterChain = FilterChain()
    var enableFilter: Boolean = false
    var enableCookie: Boolean = false

    override fun isDebug() = isDebug

    override fun createBuilder() = ThreeTenGsonAdapter.registerLocalTime(GsonBuilder())

    override fun gson() = createBuilder().create()

    override fun addDefaultHeader(): NetworkProvider {
        addHeader("Content-Type", "application/json")
        return this
    }

    override fun addHeader(key: String, value: String): NetworkProvider {
        headers[key] = value
        return this
    }

    override fun addFilter(interceptFilter: InterceptFilter): NetworkProvider {
        filterChain.addFilter(interceptFilter)
        return this
    }

    override fun clearFilter(): NetworkProvider {
        filterChain.clearFilter()
        return this
    }

    override fun enableFilter(enableFilter: Boolean): NetworkProvider {
        this.enableFilter = enableFilter
        return this
    }

    override fun enableCookie(enableCookie: Boolean): NetworkProvider {
        this.enableCookie = enableCookie
        return this
    }

    override fun addInterceptor(interceptor: Interceptor): NetworkProvider {
        interceptors.add(interceptor)
        return this
    }

    override fun addNetworkInterceptor(interceptor: Interceptor): NetworkProvider {
        networkInterceptors.add(interceptor)
        return this
    }

    override fun <T> provideApi(baseUrl: String, apiClass: Class<T>): T {
        return provideApi(baseUrl, apiClass, false)
    }

    override fun <T> provideApi(baseUrl: String, apiClass: Class<T>, enableProgress: Boolean): T {
        val builder = OkHttpClient.Builder()
        with(builder) {
            //set timeout
            val timeOut = getTimeout()
            connectTimeout(timeOut.toLong(), TimeUnit.SECONDS)
            readTimeout(timeOut.toLong(), TimeUnit.SECONDS)
            writeTimeout(timeOut.toLong(), TimeUnit.SECONDS)

            addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                if (headers.isEmpty()) {
                    addDefaultHeader()
                }
                for ((key, value) in headers) {
                    requestBuilder.header(key, value)
                }
                chain.proceed(requestBuilder.build())
            }

            //enable network calls logging for debug mode
            if (isDebug()) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.setLevel(getLevel())
                addInterceptor(httpLoggingInterceptor)
            }

            //add progress
            if (enableProgress) {
                addNetworkInterceptor { chain ->
                    val originalResponse = chain.proceed(chain.request())
                    originalResponse.newBuilder()
                        .body(
                            ProgressResponseBody(originalResponse.body(),
                                object : ProgressListener {
                                    override fun onProgressUpdate(
                                        bytesRead: Long,
                                        contentLength: Long,
                                        isDone: Boolean
                                    ) {
                                        PROGRESS_BUS.post(
                                            ProgressBus(
                                                apiClass, bytesRead, contentLength, isDone
                                            )
                                        )//To change body of created functions use File | Settings | File Templates.
                                    }
                                })
                        )
                        .build()
                }
            }

            //enable cookie
            if (enableCookie) {
                cookieJar(PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(getContext())))
            }

            //provide interceptors
            for(interceptor in interceptors) {
                addInterceptor(interceptor)
            }
            for(interceptor in networkInterceptors) {
                addInterceptor(interceptor)
            }
        }
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson()))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(builder.build())
            .build()
            .create(apiClass)
    }

    override fun <TResponse : RestMessageResponse<TResult>, TResult> transformResponse(call: Observable<TResponse>)
            : Observable<TResult> {
        return transformResponse(call, true)
    }

    override fun <TResponse : RestMessageResponse<TResult>, TResult> transformResponse(
        call: Observable<TResponse>,
        enableFilter: Boolean
    ): Observable<TResult> {

        var response: Observable<TResponse> = call.observeOn(Schedulers.computation())
        //filter with original data
        val rootFilter:  Filter<TResponse, Observable<TResponse>>? = getRootFilter()
        if (rootFilter != null) {
            response = response.flatMap {
                rootFilter.execute(it)
            }
        }
        //handle error filter
        response = response
            .onErrorResumeNext { throwable: Throwable -> NetworkFilter<TResponse>(this).execute(throwable) }
            .onErrorResumeNext { throwable: Throwable -> ApiThrowableFilter<TResponse>().execute(throwable) }

        //filter data after filter error
        val commonFilter: Filter<TResponse, Observable<TResponse>>? = getCommonFilter()
        if (commonFilter != null) {
            response = response.flatMap { commonFilter.execute(it) }
        }
        var result : Observable<TResult> = response.flatMap { Observable.just(it.getData()) }
        if (this.enableFilter && enableFilter) {
            result = filterChain.execute(result)
        }

        return result.onExceptionResumeNext(Observable.empty())
    }

    override fun <TResponse> verifyResponse(call: Observable<TResponse>): Observable<TResponse> {
        return verifyResponse(call, true)
    }

    override fun <TResponse> verifyResponse(call: Observable<TResponse>, enableFilter: Boolean)
            : Observable<TResponse> {
        var response: Observable<TResponse> = call.observeOn(Schedulers.computation())
        //filter with original data
        val rootFilter:  Filter<TResponse, Observable<TResponse>>? = getRootFilter()
        if (rootFilter != null) {
            response = response.flatMap {
                rootFilter.execute(it)
            }
        }
        //handle error filter
        response = response
            .onErrorResumeNext { throwable: Throwable -> NetworkFilter<TResponse>(this).execute(throwable) }
            .onErrorResumeNext { throwable: Throwable -> ApiThrowableFilter<TResponse>().execute(throwable) }

        //filter data after filter error
        val commonFilter: Filter<TResponse, Observable<TResponse>>? = getCommonFilter()
        if (commonFilter != null) {
            response = response.flatMap { commonFilter.execute(it) }
        }
        response = response.flatMap { Observable.just(it) }
        if (this.enableFilter && enableFilter) {
            response = filterChain.execute(response)
        }

        return response.onExceptionResumeNext(Observable.empty())
    }
}