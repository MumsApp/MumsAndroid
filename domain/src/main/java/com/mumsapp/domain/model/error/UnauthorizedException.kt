package com.mumsapp.domain.model.error

import com.mumsapp.domain.exceptions.LocalizedException

class UnauthorizedException(message: String) : LocalizedException(message) {
}