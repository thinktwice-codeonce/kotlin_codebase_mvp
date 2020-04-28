package com.codebase.mvp.service.authentication

import com.codebase.mvp.service.authentication.model.LoginRequest
import com.codebase.mvp.service.authentication.model.LoginResponse
import com.codebase.mvp.service.authentication.model.LoginSocialRequest
import rx.Observable

interface AuthenticationManager<TUser : LoginResponse, TLoginRequest : LoginRequest,
        TLoginSocialRequest : LoginSocialRequest> {

    fun isAuthenticated(): Boolean

    fun getCurrentUser(): TUser

    fun setCurrentUser(currentUser: TUser)

    fun loadFromStorage(): Boolean

    fun login(loginRequest: TLoginRequest): Observable<TUser>

    fun loginSocial(loginSocialRequest: TLoginSocialRequest): Observable<TUser>

    fun logout(): Observable<String>

    fun configure(): AuthenticationManagerConfiguration
}