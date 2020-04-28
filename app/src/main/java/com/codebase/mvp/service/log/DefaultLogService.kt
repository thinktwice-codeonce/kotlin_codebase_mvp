package com.codebase.mvp.service.log

import android.content.Context
import com.logentries.logger.AndroidLogger
import java.io.File
import java.io.IOException

class DefaultLogService : LogService{
    protected var logger: AndroidLogger? = null

    @Throws(IOException::class)
    override fun init(
        context: Context, useHttpPost: Boolean, useSsl: Boolean, isUsingDataHub: Boolean, dataHubAddr: String?,
        dataHubPort: Int, token: String, logHostName: Boolean
    ) {

        val logFile = File(context.filesDir, "LogentriesLogStorage.log")
        if (!logFile.exists()) {
            try {
                logFile.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        this.logger = AndroidLogger
            .createInstance(
                context, useHttpPost, useSsl, isUsingDataHub, dataHubAddr, dataHubPort, token,
                logHostName
            )
    }

    @Throws(IOException::class)
    override fun init(context: Context, token: String) {
        init(context, true, false, false, null, 0, token, false)
    }

    override fun log(message: String) {
        if (this.logger != null) {
            this.logger!!.log(message)
        }
    }
}