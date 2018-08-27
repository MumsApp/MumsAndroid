package com.mumsapp.android.product

import android.net.Uri
import com.mumsapp.domain.model.BaseResponse
import java.io.File

data class ImageSliderItem(val uri: Uri?, val file: File?, var isAddPhoto: Boolean, val apiUrl: String? = null) : BaseResponse() {
}