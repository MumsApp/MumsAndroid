package com.mumsapp.domain.model.error

import com.mumsapp.domain.exceptions.LocalizedException

class GoogleLoginError(message: String) : LocalizedException(message)