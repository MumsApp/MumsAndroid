package com.mumsapp.android.lobby

import com.mumsapp.android.base.LifecycleView
import com.mumsapp.domain.model.lobby.LobbyRoomTopic

interface LobbyRoomDetailsView : LifecycleView {

    fun setTitle(title: String)

    fun showTopics(topics: List<LobbyRoomTopic>, replyClickListener: (item: LobbyRoomTopic) -> Unit,
                   userClickListener: (item: LobbyRoomTopic) -> Unit)
}