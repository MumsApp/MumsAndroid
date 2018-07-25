package com.mumsapp.android.lobby

import android.net.Uri
import com.mumsapp.android.base.LifecycleView

interface CreateLobbyCategoryView : LifecycleView {

    fun showSelectImageSourceDialog(galleryClickListener: () -> Unit, cameraClickListener: () -> Unit)

    fun showCoverPhoto(uri: Uri)
}