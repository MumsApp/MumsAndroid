package com.mumsapp.android.base

import android.arch.lifecycle.LifecycleOwner

interface LifecycleView : BaseView, LifecycleOwner {

    override fun <T : BasePresenter<BaseView>> getPresenter(): T {
        return getLifecyclePresenter<LifecyclePresenter<LifecycleView>>() as T
    }

    fun <T: LifecyclePresenter<LifecycleView>> getLifecyclePresenter(): T

    fun hideBottomMenuButton() {}

    fun showBottomMenuButton() {}

    fun isBottomMenuButtonVisible() = false
}