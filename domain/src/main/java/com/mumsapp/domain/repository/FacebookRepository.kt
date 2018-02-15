package com.mumsapp.domain.repository

import com.mumsapp.domain.model.user.FacebookUserResponse
import io.reactivex.Observable

interface FacebookRepository {

    fun getFacebookUser(): Observable<FacebookUserResponse>
}