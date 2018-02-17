package com.mumsapp.data.facebook

import android.app.Activity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import java.util.*

class FacebookLoginObservable : ObservableOnSubscribe<LoginResult> {
    private val callbackManager: CallbackManager
    private val activity: Activity


    constructor(callbackManager: CallbackManager, activity: Activity) {
        this.callbackManager = callbackManager
        this.activity = activity
    }

    override fun subscribe(emitter: ObservableEmitter<LoginResult>) {
        requestLogin(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                emitter.onNext(loginResult)
                emitter.onComplete()
            }

            override fun onCancel() {
                emitter.onComplete()
            }

            override fun onError(error: FacebookException) {
                emitter.onError(error)
            }
        })
    }

    private fun requestLogin(callbackManager: CallbackManager, callback: FacebookCallback<LoginResult>) {
        val manager = LoginManager.getInstance()
        manager.registerCallback(callbackManager, callback)
        manager.logInWithReadPermissions(activity, Arrays.asList("public_profile", "email"))
    }
}