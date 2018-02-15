package com.mumsapp.android.facebook

import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.mumsapp.android.base.BaseActivity
import com.mumsapp.data.facebook.AbstractFacebookLoginObservable
import com.mumsapp.domain.model.user.FacebookUserResponse
import io.reactivex.Observer
import java.util.*
import javax.inject.Inject

class FacebookLoginObservable : AbstractFacebookLoginObservable {

    private val callbackManager: CallbackManager
    private val activity: BaseActivity

    @Inject
    constructor(callbackManager: CallbackManager, activity: BaseActivity) {
        this.callbackManager = callbackManager
        this.activity = activity
    }


    override fun subscribeActual(observer: Observer<in LoginResult>?) {

        requestLogin(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                observer?.onNext(loginResult)
            }

            override fun onCancel() {
                observer?.onComplete()
            }

            override fun onError(error: FacebookException) {
                observer?.onError(error)
            }
        })
    }

    private fun requestLogin(callbackManager: CallbackManager, callback: FacebookCallback<LoginResult>) {
        val manager = LoginManager.getInstance()
        manager.registerCallback(callbackManager, callback)
        manager.logInWithReadPermissions(activity, Arrays.asList("public_profile", "email"))
    }
}