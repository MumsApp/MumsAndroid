package com.mumsapp.data.facebook

import com.facebook.login.LoginResult
import com.mumsapp.domain.model.user.FacebookUserResponse
import io.reactivex.Observable

abstract class AbstractFacebookLoginObservable : Observable<LoginResult>()