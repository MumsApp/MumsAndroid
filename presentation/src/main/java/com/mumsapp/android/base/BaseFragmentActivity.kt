package com.mumsapp.android.base

import android.support.v4.app.FragmentManager

abstract class BaseFragmentActivity: BaseActivity() {

    abstract fun getFragmentContainerId(): Int

    abstract fun getProperFragmentManager(): FragmentManager
}