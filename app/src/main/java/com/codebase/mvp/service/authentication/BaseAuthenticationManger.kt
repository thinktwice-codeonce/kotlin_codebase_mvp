package com.codebase.mvp.service.authentication

import com.codebase.mvp.service.authentication.model.LoginRequest
import com.codebase.mvp.service.authentication.model.LoginResponse
import com.codebase.mvp.service.authentication.model.LoginSocialRequest
import com.codebase.mvp.service.network.filter.AuthenticationSuccessFilter
import com.orhanobut.hawk.Hawk
import rx.Observable

abstract class BaseAuthenticationManger<
        TUser : LoginResponse,
        TLoginRequest : LoginRequest,
        TLoginSocialRequest : LoginSocialRequest>(
    val configuration: AuthenticationManagerConfiguration
) : AuthenticationManager<TUser, TLoginRequest, TLoginSocialRequest> {

    protected lateinit var user: TUser

    override fun getCurrentUser(): TUser {
        return user
    }

    override fun setCurrentUser(currentUser: TUser) {
        this.user = currentUser
        if (configuration.useStorage && Hawk.isBuilt()) {
            if (currentUser == null) {
                Hawk.remove(configuration.uniqueStorageKey)
            } else {
                Hawk.put(configuration.uniqueStorageKey, currentUser)
            }
        }
    }

    override fun isAuthenticated(): Boolean = this.user != null

    override fun configure(): AuthenticationManagerConfiguration = configuration

    override fun loadFromStorage(): Boolean {
        var canLoad = false
        if (configuration.useStorage && Hawk.isBuilt()) {
            val user: TUser = Hawk.get(configuration.uniqueStorageKey)
            if (user != null) {
                this.user = user
                canLoad = true
            }
        }
        return canLoad
    }

    override fun login(loginRequest: TLoginRequest): Observable<TUser> {
        return onLogin(loginRequest)
            .compose(AuthenticationSuccessFilter(this).execute())
    }

    protected abstract fun onLogin(loginRequest: TLoginRequest): Observable<TUser>

    override fun loginSocial(loginSocialRequest: TLoginSocialRequest): Observable<TUser> {
        return onLoginSocial(loginSocialRequest)
            .compose(AuthenticationSuccessFilter(this).execute())
    }

    protected abstract fun onLoginSocial(loginSocialRequest: TLoginSocialRequest): Observable<TUser>

}