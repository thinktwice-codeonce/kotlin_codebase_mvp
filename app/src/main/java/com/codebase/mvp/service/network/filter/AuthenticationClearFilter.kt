package com.codebase.mvp.service.network.filter

import com.codebase.mvp.service.authentication.model.LoginRequest
import com.codebase.mvp.service.authentication.model.LoginResponse
import com.codebase.mvp.service.authentication.model.LoginSocialRequest
import rx.Observable
import rx.schedulers.Schedulers



class AuthenticationClearFilter<TUser : LoginResponse, TLoginRequest : LoginRequest, TLoginSocialRequest : LoginSocialRequest>
    : OutputFilter<Observable.Transformer<String, String>> {

    //protected var accountManager: AbstractAuthenticationManager<TUser, TLoginRequest, TLoginSocialRequest>

    /*constructor(
        accountManager: AbstractAuthenticationManager<TUser, TLoginRequest, TLoginSocialRequest>
    ){
        this.accountManager = accountManager
    }*/

    override fun execute(): Observable.Transformer<String, String> {

        return Observable.Transformer { userObservable: Observable<String> ->
            userObservable.observeOn(Schedulers.computation())
                //.doOnTerminate({ accountManager.setCurrentUser(null) })
        }
    }
}