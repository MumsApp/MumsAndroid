package com.mumsapp.data.repository

import com.mumsapp.domain.repository.ImagesRepository

class ImagesRepositoryImpl : ImagesRepository {

    private val apiUrl: String

    constructor(apiUrl: String) {
        this.apiUrl = apiUrl
    }


    override fun getApiImageUrl(path: String): String {
        return apiUrl + path
    }
}