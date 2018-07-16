package com.mumsapp.data.repository

import com.mumsapp.domain.repository.ImagesRepository

class ImagesRepositoryImpl : ImagesRepository {

    private val rootUrl: String

    constructor(rootUrl: String) {
        this.rootUrl = rootUrl
    }


    override fun getApiImageUrl(path: String): String {
        return rootUrl + path
    }
}