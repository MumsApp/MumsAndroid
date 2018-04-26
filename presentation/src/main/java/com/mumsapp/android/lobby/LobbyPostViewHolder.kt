package com.mumsapp.android.lobby

import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseViewHolder
import com.mumsapp.android.ui.views.BaseTextView
import com.mumsapp.android.ui.views.CircleImageView
import com.mumsapp.android.util.ImagesLoader
import com.mumsapp.domain.model.lobby.LobbyPost
import java.lang.ref.WeakReference

class LobbyPostViewHolder : BaseViewHolder<LobbyPost> {

    private val imagesLoader: ImagesLoader

    @BindView(R.id.cell_lobby_post_avatar)
    lateinit var avatarView: CircleImageView

    @BindView(R.id.cell_lobby_post_user_name)
    lateinit var userNameView: BaseTextView

    @BindView(R.id.cell_lobby_post_date)
    lateinit var dateView: BaseTextView

    @BindView(R.id.cell_lobby_post_title)
    lateinit var titleView: BaseTextView

    @BindView(R.id.cell_lobby_post_content)
    lateinit var contentView: BaseTextView

    private var listener: WeakReference<((item: LobbyPost) -> Unit)>? = null

    private var item: LobbyPost? = null

    constructor(imagesLoader: ImagesLoader, itemView: View) : super(itemView) {
        this.imagesLoader = imagesLoader
        ButterKnife.bind(this, itemView)
    }

    override fun init(item: LobbyPost) {
        this.item = item

        userNameView.text = item.userName
        dateView.text = item.date
        titleView.text = item.title
        contentView.text = item.content
    }

    fun setReplyListener(listener: (item: LobbyPost) -> Unit) {
        this.listener = WeakReference(listener)
    }

    @OnClick(R.id.cell_lobby_post_reply)
    fun onReplyClick() {
        listener?.get()?.invoke(item!!)
    }
}