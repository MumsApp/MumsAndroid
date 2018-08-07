package com.mumsapp.android.lobby

import com.mumsapp.android.base.LifecycleView
import com.mumsapp.domain.model.lobby.LobbyRoomTopic
import com.mumsapp.domain.model.lobby.LobbyRoomTopicPost

interface LobbyTopicDetailsView : LifecycleView {

    fun setTitle(title: String)

    fun showPosts(topics: List<LobbyRoomTopicPost>, userClickListener: (item: LobbyRoomTopicPost) -> Unit)

    fun setupPagination(lastPage: Int, pageChangeListener: ((page: Int) -> Unit)?)
}