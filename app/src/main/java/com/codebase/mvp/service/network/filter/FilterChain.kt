package com.bss.codebase.service.network.filter

import rx.Observable
import java.util.ArrayList

class FilterChain {

    protected var filters: MutableList<InterceptFilter> = ArrayList()

    fun <T> execute(target: Observable<T>): Observable<T> {
        var res = target
        for (filter in filters) {
            res = target.compose(filter.execute())
        }
        return res
    }

    fun addFilter(filter: InterceptFilter) {
        filters.add(filter)
    }

    fun clearFilter() {
        filters.clear()
    }

}