package com.mumsapp.android.common.features

interface HasOverlays {

    fun showLoading()

    fun hideOverlays()

    fun isLoadingPresented(): Boolean

    fun isSessionExpiredPresented(): Boolean
    
    fun showSessionExpired()
}