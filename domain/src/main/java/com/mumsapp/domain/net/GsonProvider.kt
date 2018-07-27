package com.mumsapp.domain.net

import com.google.gson.Gson

interface GsonProvider {

    fun provideGson(): Gson
}