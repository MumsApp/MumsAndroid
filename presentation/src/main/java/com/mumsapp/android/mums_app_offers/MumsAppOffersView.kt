package com.mumsapp.android.mums_app_offers

import com.mumsapp.android.base.LifecycleView
import com.mumsapp.domain.model.chat.TemplateChatThread

interface MumsAppOffersView : LifecycleView {

    fun showItems(items: List<TemplateChatThread>)
}