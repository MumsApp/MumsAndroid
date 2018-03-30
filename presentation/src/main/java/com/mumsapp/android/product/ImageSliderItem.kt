package com.mumsapp.android.product

import android.net.Uri
import com.mumsapp.domain.model.BaseResponse

data class ImageSliderItem(val uri: Uri?, var isAddPhoto: Boolean) : BaseResponse() {
}