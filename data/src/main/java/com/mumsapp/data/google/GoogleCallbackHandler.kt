package com.mumsapp.data.google

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoogleCallbackHandler {

    companion object {
        val GOOGLE_SIGN_IN_REQUEST_CODE = 30
    }

    private var receiver: ResultReceiver? = null

    @Inject
    constructor()

    fun sendResult(task: Task<GoogleSignInAccount>) {
        receiver?.handleResult(task)
    }

    fun registerReceiver(receiver: ResultReceiver) {
        this.receiver = receiver
    }

    fun unregisterReceiver() {
        this.receiver = null
    }

    interface ResultReceiver {
        fun handleResult(task: Task<GoogleSignInAccount>)
    }
}