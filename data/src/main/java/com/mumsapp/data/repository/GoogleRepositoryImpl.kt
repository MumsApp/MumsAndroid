package com.mumsapp.data.repository

import android.app.Activity
import com.mumsapp.data.google.GoogleCallbackHandler
import com.mumsapp.data.google.GoogleLoginObservable
import com.mumsapp.domain.model.user.GoogleUserResponse
import com.mumsapp.domain.repository.GoogleRepository
import io.reactivex.Observable
import javax.inject.Inject

class GoogleRepositoryImpl : GoogleRepository {

    private val callbackHandler: GoogleCallbackHandler
    private val activity: Activity

    @Inject
    constructor(callbackHandler: GoogleCallbackHandler, activity: Activity) {
        this.callbackHandler = callbackHandler
        this.activity = activity
    }

    override fun getLoggedUser(): Observable<GoogleUserResponse> {
        return Observable.create(GoogleLoginObservable(callbackHandler, activity))
    }
}