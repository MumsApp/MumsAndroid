package com.mumsapp.android.product

import android.net.Uri
import com.mumsapp.android.base.LifecycleView

interface AddProductView : LifecycleView {

    fun showSelectImageSourceDialog()

    fun showImageHeader(uri: Uri)
}