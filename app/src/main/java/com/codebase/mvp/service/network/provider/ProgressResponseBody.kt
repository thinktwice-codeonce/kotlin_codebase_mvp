package com.bss.codebase.service.network.provider

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.BufferedSource

class ProgressResponseBody(
   val responseBody: ResponseBody?,
   val progressListener: ProgressListener
) : ResponseBody() {

    override fun contentLength(): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun contentType(): MediaType? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun source(): BufferedSource {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}