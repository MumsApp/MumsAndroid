package com.mumsapp.data.repository

import android.app.Activity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.mumsapp.data.facebook.FacebookLoginObservable
import com.mumsapp.data.facebook.FacebookProfileObservable
import com.mumsapp.domain.model.user.FacebookUserResponse
import com.mumsapp.domain.repository.FacebookRepository
import io.reactivex.Observable
import javax.inject.Inject

class FacebookRepositoryImpl : FacebookRepository {

    private val callbackManager: CallbackManager
    private val activity: Activity

    @Inject
    constructor(callbackManager: CallbackManager, activity: Activity) {
        this.callbackManager = callbackManager
        this.activity = activity
    }

    override fun getFacebookUser(): Observable<FacebookUserResponse> {
        return Observable.create(FacebookLoginObservable(callbackManager, activity))
                .flatMap { executeGraphResponse(it.accessToken) }
    }

    private fun executeGraphResponse(accessToken: AccessToken): Observable<FacebookUserResponse> {
        return Observable.create(FacebookProfileObservable(accessToken))
    }
}