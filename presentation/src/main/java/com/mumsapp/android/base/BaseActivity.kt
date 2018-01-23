package com.mumsapp.android.base

import android.support.v7.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity() {

    abstract fun prepareActivityComponent()

    abstract fun <T: BasePresenter> getPresenter(): T
}