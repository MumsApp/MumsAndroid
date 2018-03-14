package com.mumsapp.android.chat

import com.mumsapp.android.base.LifecyclePresenter
import javax.inject.Inject

class ChatListPresenter : LifecyclePresenter<ChatListView> {

    @Inject
    constructor() {

    }

    fun onFiltersButtonClick() {

    }

    fun onSettingsButtonClick() {
        view?.openChatSettingsDialog()
    }

    fun onSearch(value: String) {

    }
}