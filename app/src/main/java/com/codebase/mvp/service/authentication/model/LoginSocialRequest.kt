package com.codebase.mvp.service.authentication.model

interface LoginSocialRequest {
    fun getSocialToken(): String

    fun setSocialToken(socialToken: String)

    fun getPlatform(): String

    fun setPlatform(platform: String)
}