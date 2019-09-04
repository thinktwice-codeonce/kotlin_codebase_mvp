package com.bss.codebase.service.network.provider

interface ProgressListener {
    fun onProgressUpdate(bytesRead: Long, contentLength: Long, isDone: Boolean)
}