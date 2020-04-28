package com.codebase.mvp.service.network.provider

import android.content.Context
import android.net.ConnectivityManager
import com.codebase.mvp.service.network.filter.Filter
import com.codebase.mvp.service.network.intercepter.HttpLoggingInterceptor
import com.google.gson.Gson
import rx.Observable
import rx.schedulers.Schedulers

abstract class BaseNetworkProvider(private val context: Context): NetworkProvider {

    protected abstract fun gson() : Gson

    override fun getContext(): Context = context

    override fun getTimeout(): Int = 120

    override fun getLevel(): HttpLoggingInterceptor.Level {
        return HttpLoggingInterceptor.Level.BODY
    }

    override fun isNetworkAvailable(): Boolean {
        val connectivityManager: ConnectivityManager = getContext().getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    override fun <TResponse> verifyResponse(call: Observable<TResponse>): Observable<TResponse> {
        return call
            .observeOn(Schedulers.computation())
            .onExceptionResumeNext(Observable.empty())
            .flatMap { Observable.just(it) }
    }

    override fun <TResponse> getRootFilter(): Filter<TResponse, Observable<TResponse>>? {
        return null
    }

    override fun <TResponse> getCommonFilter(): Filter<TResponse, Observable<TResponse>>? {
        return null
    }
}