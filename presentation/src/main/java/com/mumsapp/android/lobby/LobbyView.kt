package com.mumsapp.android.lobby

import com.mumsapp.android.base.LifecycleView
import com.mumsapp.domain.model.lobby.LobbyItem

interface LobbyView : LifecycleView {

    fun showItems(items: List<LobbyItem>, switchChangeListener: (item: LobbyItem, value: Boolean) -> Unit)
}