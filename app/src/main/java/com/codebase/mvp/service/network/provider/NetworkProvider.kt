package com.codebase.mvp.service.network.provider

import android.content.Context
import com.codebase.mvp.service.common.RestMessageResponse
import com.codebase.mvp.service.network.filter.Filter
import com.codebase.mvp.service.network.filter.InterceptFilter
import com.codebase.mvp.service.network.intercepter.HttpLoggingInterceptor
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import rx.Observable

interface NetworkProvider {

     fun isDebug(): Boolean

     fun isNetworkAvailable(): Boolean

     fun getContext(): Context

     fun createBuilder(): GsonBuilder

     fun addDefaultHeader(): NetworkProvider

     fun addHeader(key: String, value: String): NetworkProvider

     fun addFilter(interceptFilter: InterceptFilter): NetworkProvider

     fun clearFilter(): NetworkProvider

     fun enableFilter(enableFilter: Boolean): NetworkProvider

     fun enableCookie(enableCookie: Boolean): NetworkProvider

     fun addInterceptor(interceptor: Interceptor): NetworkProvider

     fun addNetworkInterceptor(interceptor: Interceptor): NetworkProvider

     fun getLevel(): HttpLoggingInterceptor.Level

     fun getTimeout(): Int

     fun <T> provideApi(baseUrl: String, apiClass: Class<T>): T

     fun <T> provideApi(baseUrl: String, apiClass: Class<T>, enableProgress: Boolean): T

     fun <TResponse : RestMessageResponse<TResult>, TResult> transformResponse(
        call: Observable<TResponse>
    ): Observable<TResult>

     fun <TResponse : RestMessageResponse<TResult>, TResult> transformResponse(
        call: Observable<TResponse>, enableFilter: Boolean
    ): Observable<TResult>

     fun <TResponse> verifyResponse(call: Observable<TResponse>): Observable<TResponse>

     fun <TResponse> verifyResponse(call: Observable<TResponse>, enableFilter: Boolean): Observable<TResponse>

     fun <TResponse> getRootFilter(): Filter<TResponse, Observable<TResponse>>?

     fun <TResponse> getCommonFilter(): Filter<TResponse, Observable<TResponse>>?
}