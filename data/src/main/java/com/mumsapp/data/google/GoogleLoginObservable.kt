package com.mumsapp.data.google

import android.app.Activity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.mumsapp.android.data.BuildConfig
import com.mumsapp.domain.model.user.GoogleUserResponse
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe

class GoogleLoginObservable(private val callbackHandler: GoogleCallbackHandler,
                            private val context: Activity) :
        ObservableOnSubscribe<GoogleUserResponse>, GoogleCallbackHandler.ResultReceiver {

    private var emitter: ObservableEmitter<GoogleUserResponse>? = null


    override fun subscribe(emitter: ObservableEmitter<GoogleUserResponse>) {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestId()
                .requestProfile()
                .requestIdToken(BuildConfig.GOOGLE_SIGN_IN_KEY)
                .build()

        val client = GoogleSignIn.getClient(context, options)
        var account = GoogleSignIn.getLastSignedInAccount(context)

        if(account != null) {
            val response = createUserFromAccount(account)
            emitter.onNext(response)
            emitter.onComplete()
            return
        }

        this.emitter = emitter
        callbackHandler.registerReceiver(this)
        requestSignIn(client)
    }

    private fun createUserFromAccount(account: GoogleSignInAccount): GoogleUserResponse {
        return GoogleUserResponse(account.givenName!!, account.familyName!!, account.email!!, account.idToken!!)
    }

    private fun requestSignIn(client: GoogleSignInClient) {
        val intent = client.signInIntent
        context.startActivityForResult(intent, GoogleCallbackHandler.GOOGLE_SIGN_IN_REQUEST_CODE)
    }

    override fun handleResult(task: Task<GoogleSignInAccount>) {
        callbackHandler.unregisterReceiver()
        if(task.isSuccessful) {
            val response = createUserFromAccount(task.result)
            emitter?.onNext(response)
            emitter?.onComplete()
        } else {
            emitter?.onError(task.exception!!)
        }
    }
}