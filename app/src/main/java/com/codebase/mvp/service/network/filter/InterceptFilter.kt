package com.codebase.mvp.service.network.filter

import rx.Observable

interface InterceptFilter {

    fun <T> execute(): Observable.Transformer<T, T>

}