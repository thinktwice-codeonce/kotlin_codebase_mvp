package com.codebase.mvp.models

import com.codebase.mvp.exception.ErrorCodes

class ServiceResultError {
    private var errorCode: Int = 0
    private var errorMessage: String? = null
    private var exception: Exception? = null

    constructor (errorCode: Int, errorMessage: String?, exception: Exception) {
        this.errorCode = errorCode
        this.errorMessage = errorMessage
        this.exception = exception
    }

    constructor(errorCode: Int, errorMessage: String){
        ServiceResultError(errorCode, errorMessage, Exception(errorMessage))
    }

    constructor(errorMessage: String) {
        ServiceResultError(ErrorCodes.GENERAL_ERROR, errorMessage)
    }

    constructor(errorCode: Int){
        ServiceResultError(errorCode, String.format("ERROR MESSAGE OF CODE %s", errorCode))
    }

    constructor(exception: Exception) {
        ServiceResultError(ErrorCodes.GENERAL_ERROR, exception.message, exception)
    }

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

    fun getException(): Exception? {
        return exception
    }

    fun setException(exception: Exception) {
        this.exception = exception
    }
}