package com.mumsapp.android.main

import com.mumsapp.android.base.LifecycleView

interface MainView : LifecycleView {

    fun showMenu()

    fun hideMenu()

    fun showEmergencyButton()

    fun hideEmergencyButton()
}