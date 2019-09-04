package com.bss.codebase.service.network.filter

import com.bss.codebase.service.authentication.BaseAuthenticationManger
import com.bss.codebase.service.authentication.model.LoginRequest
import com.bss.codebase.service.authentication.model.LoginResponse
import com.bss.codebase.service.authentication.model.LoginSocialRequest
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