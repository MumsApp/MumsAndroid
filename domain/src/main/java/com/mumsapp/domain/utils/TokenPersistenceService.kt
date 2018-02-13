package com.mumsapp.domain.utils

import com.mumsapp.domain.model.identity.Token
import io.reactivex.annotations.NonNull

interface TokenPersistenceService {

    fun loadToken(): Token?

    fun saveToken(@NonNull token: Token)

    fun clearToken()

    fun getToken(): Token?
}