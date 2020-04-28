package com.codebase.mvp.service.authentication.model

interface LoginResponse {
    fun getUsername(): String

    fun setUsername(username: String)

    fun getEmail(): String

    fun setEmail(email: String)

    fun getAccessToken(): String

    fun setAccessToken(token: String)
}