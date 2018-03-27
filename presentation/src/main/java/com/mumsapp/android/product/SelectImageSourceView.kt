package com.mumsapp.android.product

import com.mumsapp.android.base.BaseView

interface SelectImageSourceView : BaseView {

    fun dismissView()

    fun deliverGalleryResults()

    fun deliverCameraResults()
}