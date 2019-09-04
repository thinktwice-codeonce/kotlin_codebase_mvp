package com.bss.codebase.service.network.filter

interface Filter<Input, Output> {
    fun execute(source: Input) : Output
}