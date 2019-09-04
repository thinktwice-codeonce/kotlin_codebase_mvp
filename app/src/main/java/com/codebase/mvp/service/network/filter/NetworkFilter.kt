package com.bss.codebase.service.network.filter

import com.bss.codebase.exception.ApiThrowable
import com.bss.codebase.exception.ErrorCodes
import com.bss.codebase.service.network.provider.NetworkProvider
import rx.Observable

class NetworkFilter<T>(networkProvider: NetworkProvider) : Filter<Throwable, Observable<T>> {

    protected var networkProvider: NetworkProvider? = networkProvider

    override fun execute(throwable: Throwable): Observable<T> {
        return if (!networkProvider!!.isNetworkAvailable()) {
            Observable.error(
                ApiThrowable.from(
                    ErrorCodes.NETWORK_NOT_AVAILABLE_ERROR,
                    "Network is not available"
                )
            )
        } else Observable.error<T>(throwable)
    }
}