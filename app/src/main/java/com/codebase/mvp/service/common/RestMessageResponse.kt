package com.bss.codebase.service.common

import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class RestMessageResponse<T> {

    private var data: T? = null

    private var errors: List<RestErrorResponse> = ArrayList()

    fun getData(): T? {
        return data
    }

    fun setData(data: T) {
        this.data = data
    }

    fun getErrors(): List<RestErrorResponse> {
        return errors
    }

    fun setErrors(errors: List<RestErrorResponse>) {
        this.errors = errors
    }
}