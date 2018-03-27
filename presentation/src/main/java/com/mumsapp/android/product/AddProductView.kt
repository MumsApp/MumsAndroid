package com.mumsapp.android.product

import com.mumsapp.android.base.LifecycleView

interface AddProductView : LifecycleView {

    fun showSelectImageSourceDialog(galleryClickListener: () -> Unit,
                                    cameraClickListener: () -> Unit)
}