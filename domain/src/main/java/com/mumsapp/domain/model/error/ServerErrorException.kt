package com.mumsapp.domain.model.error

import com.mumsapp.domain.exceptions.LocalizedException

class ServerErrorException(message: String) : LocalizedException(message)