package com.codebase.mvp.service.network.filter

interface Filter<Input, Output> {
    fun execute(source: Input) : Output
}