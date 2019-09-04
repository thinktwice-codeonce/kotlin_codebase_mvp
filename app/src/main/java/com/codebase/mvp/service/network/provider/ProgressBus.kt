package com.bss.codebase.service.network.provider

import com.google.gson.Gson

class ProgressBus(
    val apiClass: Class<*>,
    val bytesRead: Long,
    val contentLength: Long,
    val isDone: Boolean
) {
    override fun toString(): String {
        return Gson().toJson(this)
    }
}