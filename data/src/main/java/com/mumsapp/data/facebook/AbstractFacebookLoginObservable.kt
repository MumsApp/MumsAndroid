package com.mumsapp.data.facebook

import com.facebook.login.LoginResult
import io.reactivex.ObservableOnSubscribe

abstract class AbstractFacebookLoginObservable : ObservableOnSubscribe<LoginResult>