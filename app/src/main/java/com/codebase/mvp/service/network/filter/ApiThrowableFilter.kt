package com.codebase.mvp.service.network.filter

import com.codebase.mvp.exception.ApiThrowable
import com.codebase.mvp.exception.ErrorCodes
import com.codebase.mvp.models.ServiceResultError
import com.codebase.mvp.service.common.MessageResponse
import com.codebase.mvp.service.common.RestMessageResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.HttpException
import rx.Observable
import java.util.ArrayList


@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE", "SpellCheckingInspection")
class ApiThrowableFilter<T> : Filter<Throwable, Observable<T>> {

    private fun onHandleFailedResponse(responseCode: Int, rawString: String): ApiThrowable {
        var exception: ApiThrowable
        try {
            val gson = Gson()
            val collectionType = object : TypeToken<RestMessageResponse<T>>() {}.type
            val responseMessage = gson.fromJson<RestMessageResponse<T>>(rawString, collectionType)
            val errors = responseMessage.getErrors()
            if (errors.isEmpty()) {
                exception = try {
                    val messageResponse = gson.fromJson<MessageResponse>(rawString, MessageResponse::class.java)
                    ApiThrowable.from(responseCode, messageResponse.getMessage()!!)
                } catch (ex: Exception) {
                    ApiThrowable.from(responseCode, rawString)
                }
            } else {
                val serviceResultErrors = ArrayList<ServiceResultError>()
                for (error in errors) {
                    serviceResultErrors.add(ServiceResultError(error.getErrorCode(), error.getErrorMessage()!!))
                }

                exception = ApiThrowable.from(serviceResultErrors)
            }
        } catch (e: Exception) {
            exception = ApiThrowable.from(e)
        }
        return exception
    }

    override fun execute(throwable: Throwable): Observable<T> {

        if (throwable is HttpException) {
            val failedResponse = throwable.response().errorBody()
            val responseCode = throwable.response().code()
            if (failedResponse == null) {
                return Observable.error(
                    ApiThrowable.from(
                        responseCode,
                        "Response Error Body is empty"
                    )
                )
            } else {
                var rawString = ""
                return try {
                    rawString = failedResponse.string()
                    Observable.error(onHandleFailedResponse(responseCode, rawString))
                } catch (ex: Exception) {
                    Observable
                        .error(ApiThrowable.from(ErrorCodes.GENERAL_ERROR, rawString, ex))
                }
            }
        }
        return Observable.error(throwable)
    }
}