package com.mumsapp.android.base

import android.arch.lifecycle.LifecycleOwner
import android.view.View
import com.mumsapp.android.common.features.HasOverlays

interface LifecycleView : BaseView, LifecycleOwner {
}