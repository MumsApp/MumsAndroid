package com.mumsapp.domain.repository

import com.mumsapp.domain.model.user.GoogleUserResponse
import io.reactivex.Observable

interface GoogleRepository {

    fun getLoggedUser(): Observable<GoogleUserResponse>
}