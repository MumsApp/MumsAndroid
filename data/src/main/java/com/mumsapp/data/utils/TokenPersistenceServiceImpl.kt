package com.mumsapp.data.utils

import com.mumsapp.domain.model.identity.Token
import com.mumsapp.domain.utils.SharedPreferencesManager
import com.mumsapp.domain.utils.TokenPersistenceService
import javax.inject.Inject

class TokenPersistenceServiceImpl : TokenPersistenceService {

    private val tokenIdKey = "TokenId"
    private val tokenKey = "Token"
    private val refreshTokenKey = "RefreshToken"

    private val sharedPreferencesManager: SharedPreferencesManager
    private var token: Token? = null

    @Inject
    constructor(sharedPreferencesManager: SharedPreferencesManager) {
        this.sharedPreferencesManager = sharedPreferencesManager
    }


    override fun loadToken(): Token? {
        val id = sharedPreferencesManager.getInt(tokenIdKey, 0)
        val token = sharedPreferencesManager.getString(tokenKey, null)
        val refreshToken = sharedPreferencesManager.getString(refreshTokenKey, null)

        if(token != null && refreshToken != null) {
            return Token(id, token, refreshToken)
        }

        return null
    }

    override fun saveToken(token: Token) {
        sharedPreferencesManager.putInt(tokenIdKey, token.id)
        sharedPreferencesManager.putString(tokenKey, token.token)
        sharedPreferencesManager.putString(refreshTokenKey, token.refreshToken)

        this.token = token
    }

    override fun clearToken() {
        sharedPreferencesManager.putInt(tokenIdKey, 0)
        sharedPreferencesManager.putString(tokenKey, null)
        sharedPreferencesManager.putString(refreshTokenKey, null)
    }

    override fun getToken(): Token? {
        if(token == null) {
            token = loadToken()
        }

        return token
    }
}