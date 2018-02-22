package com.mumsapp.domain.repository

interface ImagesRepository {

    fun getApiImageUrl(path: String): String
}