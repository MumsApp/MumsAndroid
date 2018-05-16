package com.mumsapp.android.lobby

import com.mumsapp.android.base.LifecycleView
import com.mumsapp.domain.model.lobby.LobbyPost

interface LobbyCategoryDetailsView : LifecycleView {

    fun showPosts(posts: List<LobbyPost>, replyClickListener: (item: LobbyPost) -> Unit,
                  userClickListener: (item: LobbyPost) -> Unit)
}