package com.bss.codebase.service.authentication.model

interface LoginSocialRequest {
    fun getSocialToken(): String

    fun setSocialToken(socialToken: String)

    fun getPlatform(): String

    fun setPlatform(platform: String)
}