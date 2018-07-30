package com.mumsapp.android.lobby

import android.net.Uri
import com.mumsapp.android.base.LifecycleView

interface CreateLobbyTopicView : LifecycleView {

    fun showSelectImageSourceDialog()

    fun showImage(uri: Uri)
}