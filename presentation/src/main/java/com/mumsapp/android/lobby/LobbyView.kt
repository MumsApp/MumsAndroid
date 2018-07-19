package com.mumsapp.android.lobby

import com.mumsapp.android.base.LifecycleView
import com.mumsapp.domain.model.lobby.LobbyRoom

interface LobbyView : LifecycleView {

    fun showItems(items: List<LobbyRoom>, switchChangeListener: (item: LobbyRoom, value: Boolean) -> Unit,
                  leaveListener: (item: LobbyRoom) -> Unit)

    fun showConfirmationDialog(title: String, description: String, confirmButtonText: String, confirmButtonListener: () -> Unit)
}