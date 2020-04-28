package com.codebase.mvp.service.log

import android.content.Context
import java.io.IOException

interface LogService {

    @Throws(IOException::class)
    fun init(
        context: Context,
        useHttpPost: Boolean,
        useSsl: Boolean,
        isUsingDataHub: Boolean,
        dataHubAddr: String?,
        dataHubPort: Int,
        token: String,
        logHostName: Boolean
    )

    @Throws(IOException::class)
    fun init(context: Context, token: String)

    fun log(message: String)

}