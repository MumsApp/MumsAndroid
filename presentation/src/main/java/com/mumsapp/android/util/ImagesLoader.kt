package com.mumsapp.android.util

import android.content.Context
import android.widget.ImageView
import com.mumsapp.android.BuildConfig
import com.squareup.picasso.Picasso
import javax.inject.Inject

class ImagesLoader {

    private val context: Context

    @Inject
    constructor(context: Context) {
        this.context = context
    }

    fun load(url: String, imageView: ImageView) {
        Picasso.with(context).load(url).into(imageView)
    }
}