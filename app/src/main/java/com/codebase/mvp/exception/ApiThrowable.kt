package com.bss.codebase.exception

import com.bss.codebase.models.ServiceResultError
import java.util.*

class ApiThrowable : Throwable{

    constructor()

    private var errors: List<ServiceResultError> = ArrayList()

    companion object {
        fun from(errors: List<ServiceResultError>): ApiThrowable {
            val exception = ApiThrowable()
            exception.errors = errors

            return exception
        }

        fun from(error: ServiceResultError): ApiThrowable {
            return from(ArrayList(Collections.singletonList(error)))
        }

        fun from(errorCode: Int, errorMessage: String, ex: Exception): ApiThrowable {
            return from(ServiceResultError(errorCode, errorMessage, ex))
        }

        fun from(errorCode: Int, errorMessage: String): ApiThrowable {
            return from(errorCode, errorMessage, Exception(errorMessage))
        }

        fun from(errorMessage: String): ApiThrowable {
            return from(ErrorCodes.GENERAL_ERROR, errorMessage, Exception(errorMessage))
        }

        fun from(errorCode: Int): ApiThrowable {
            return from(errorCode, String.format("ERROR MESSAGE OF CODE %s", errorCode))
        }

        fun from(exception: Exception): ApiThrowable {
            return from(
                ErrorCodes.GENERAL_ERROR,
                if (exception.message == null) exception.toString() else exception.message!!,
                exception
            )
        }
    }

    fun getErrors(): List<ServiceResultError> {
        return errors
    }

    fun hasMultipleErrors(): Boolean {
        return this.errors.size > 1
    }

    fun firstError(): ServiceResultError {
        return errors[0]
    }

    fun firstErrorCode(): Int {
        val error = firstError()
        return error.getErrorCode()
    }

    fun firstErrorMessage(): String? {
        val error = firstError()
        return error.getErrorMessage()
    }

    override val message: String?
        get() = getEMessage()

    fun getEMessage() : String {
        val builder = StringBuilder()
        for (error in this.getErrors()) {
            val errorCode = error.getErrorCode()
            val errorMessage = error.getErrorMessage()
            val exceptionMessage = error.getException()?.message

            builder.append(
                String.format(
                    "ERROR CODE: %s\nERROR MESSAGE: %s\nEXCEPTION MESSAGE: %s\n=====", errorCode,
                    errorMessage, exceptionMessage
                )
            )
        }

        return builder.toString()
    }

    fun firstErrorMessageIfAny(): String {
        val firstError = firstErrorIfAny()
        return if (firstError == null)
            "NO ERROR TO DISPLAY"
        else
            String.format(
                "GENERIC ERROR MSG: %s\nDETAIL ERROR MSG: %s", firstError.getErrorMessage(),
                if (firstError.getException() == null) "N/A" else firstError.getErrorMessage()
            )
    }

    fun firstErrorCodeIfAny(): Int {
        val firstError = firstErrorIfAny()
        return if (firstError == null) -1 else firstError!!.getErrorCode()
    }

    private fun firstErrorIfAny(): ServiceResultError? {
        var error: ServiceResultError? = null

        if (this.errors.size > 0) {
            error = this.errors[0]
        }

        return error
    }
}