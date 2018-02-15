package com.mumsapp.data.repository

import com.facebook.AccessToken
import com.mumsapp.data.facebook.AbstractFacebookLoginObservable
import com.mumsapp.data.facebook.FacebookProfileObservable
import com.mumsapp.domain.model.user.FacebookUserResponse
import com.mumsapp.domain.repository.FacebookRepository
import io.reactivex.Observable
import javax.inject.Inject

class FacebookRepositoryImpl : FacebookRepository {

    private val facebookLoginObservable: AbstractFacebookLoginObservable

    @Inject
    constructor(facebookLoginObservable: AbstractFacebookLoginObservable) {
        this.facebookLoginObservable = facebookLoginObservable
    }

    override fun getFacebookUser(): Observable<FacebookUserResponse> {
        return facebookLoginObservable
                .flatMap { executeGraphResponse(it.accessToken) }
    }

    private fun executeGraphResponse(accessToken: AccessToken): Observable<FacebookUserResponse> {
        return FacebookProfileObservable(accessToken)
    }
}