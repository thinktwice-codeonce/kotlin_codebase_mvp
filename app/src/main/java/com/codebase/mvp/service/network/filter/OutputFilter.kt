package com.bss.codebase.service.network.filter

interface OutputFilter<Output> {
    fun execute(): Output
}