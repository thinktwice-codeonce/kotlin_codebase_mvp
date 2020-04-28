package com.codebase.mvp.service.common

class RestErrorResponse {

    private var errorCode: Int = 0
    private var errorMessage: String? = null

    fun getErrorCode(): Int {
        return errorCode
    }

    fun setErrorCode(errorCode: Int) {
        this.errorCode = errorCode
    }

    fun getErrorMessage(): String? {
        return errorMessage
    }

    fun setErrorMessage(errorMessage: String) {
        this.errorMessage = errorMessage
    }

}