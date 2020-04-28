package com.codebase.mvp.service.network.filter

import com.codebase.mvp.service.authentication.BaseAuthenticationManger
import com.codebase.mvp.service.authentication.model.LoginRequest
import com.codebase.mvp.service.authentication.model.LoginResponse
import com.codebase.mvp.service.authentication.model.LoginSocialRequest
import rx.Observable
import rx.schedulers.Schedulers

class AuthenticationSuccessFilter<
        TUser : LoginResponse,
        TLoginRequest : LoginRequest,
        TLoginSocialRequest: LoginSocialRequest
        >(private var accountManager: BaseAuthenticationManger<TUser, TLoginRequest, TLoginSocialRequest>)
    : OutputFilter<Observable.Transformer<TUser, TUser>>{

    override fun execute(): Observable.Transformer<TUser, TUser> {
        return Observable.Transformer { userObservable ->
            userObservable
                .observeOn(Schedulers.computation())
                .flatMap({ user ->
                    accountManager.setCurrentUser(user)
                    Observable.just<TUser>(user)
                })
        }
    }
}