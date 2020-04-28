package com.codebase.mvp.service.authentication.model

interface LoginRequest {
    fun getUsername(): String

    fun setUsername(username: String)

    fun getPassword(): String

    fun setPassword(password: String)
}