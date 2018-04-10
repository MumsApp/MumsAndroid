package com.mumsapp.android.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squareup.picasso.Picasso
import com.stfalcon.chatkit.commons.ImageLoader
import javax.inject.Inject

class ImagesLoader : ImageLoader {

    private val context: Context

    @Inject
    constructor(context: Context) {
        this.context = context
    }

    fun load(url: String, imageView: ImageView) {
        Glide.with(context).load(url).into(imageView)
    }

    fun load(uri: Uri, imageView: ImageView) {
        Glide.with(context).load(uri).into(imageView)
    }

    fun load(uri: Uri, imageView: ImageView, resizeInPercent: Float) {
        val config = BitmapFactory.Options()
        config.inJustDecodeBounds = true

        val stream = context.contentResolver.openInputStream(uri)
        BitmapFactory.decodeStream(stream, null, config)
        stream.close()

        val reqWidth = (config.outWidth * resizeInPercent).toInt()
        val reqHeight = (config.outHeight * resizeInPercent).toInt()

        val options = RequestOptions.overrideOf(reqWidth, reqHeight)
        Glide.with(context).load(uri).apply(options).into(imageView)
    }

    override fun loadImage(imageView: ImageView?, url: String?) {
        load(url!!, imageView!!)
    }
}