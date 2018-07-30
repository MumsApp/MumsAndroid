package com.mumsapp.android.lobby

import android.net.Uri
import com.mumsapp.android.base.LifecycleView

interface CreateLobbyTopicView : LifecycleView {

    fun showSelectImageSourceDialog(galleryClickListener: () -> Unit, cameraClickListener: () -> Unit)

    fun showImage(uri: Uri)
}