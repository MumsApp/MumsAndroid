package com.mumsapp.domain.model.error

import com.mumsapp.domain.exceptions.LocalizedException

class FacebookLoginError(message: String) : LocalizedException(message) {
}