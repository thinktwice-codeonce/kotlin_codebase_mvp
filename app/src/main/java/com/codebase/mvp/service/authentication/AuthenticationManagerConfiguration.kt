package com.bss.codebase.service.authentication

class AuthenticationManagerConfiguration() {

    companion object {
        fun init(): AuthenticationManagerConfiguration {
            return AuthenticationManagerConfiguration()
        }
    }

    var useStorage: Boolean = false
    var uniqueStorageKey: String = ""

    fun useStorage(uniqueStorageKey: String): AuthenticationManagerConfiguration {
        useStorage = true
        this.uniqueStorageKey = uniqueStorageKey
        return this
    }


    fun enableStorage(): AuthenticationManagerConfiguration {
        useStorage = true
        return this
    }

    fun disableStorage(): AuthenticationManagerConfiguration {
        useStorage = false
        return this
    }
}