package com.codebase.mvp.service.network.filter

interface OutputFilter<Output> {
    fun execute(): Output
}