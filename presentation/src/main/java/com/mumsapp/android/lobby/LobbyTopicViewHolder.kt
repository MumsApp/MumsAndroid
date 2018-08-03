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
import com.mumsapp.domain.model.lobby.LobbyRoomTopic
import com.mumsapp.domain.utils.DateManager
import java.lang.ref.WeakReference

class LobbyTopicViewHolder : BaseViewHolder<LobbyRoomTopic> {

    private val imagesLoader: ImagesLoader
    private val dateManager: DateManager

    @BindView(R.id.cell_lobby_topic_avatar)
    lateinit var avatarView: CircleImageView

    @BindView(R.id.cell_lobby_topic_user_name)
    lateinit var userNameView: BaseTextView

    @BindView(R.id.cell_lobby_topic_date)
    lateinit var dateView: BaseTextView

    @BindView(R.id.cell_lobby_topic_title)
    lateinit var titleView: BaseTextView

    @BindView(R.id.cell_lobby_topic_content)
    lateinit var contentView: BaseTextView

    private var replyListener: WeakReference<((item: LobbyRoomTopic) -> Unit)>? = null
    private var avatarClickListener: WeakReference<((item: LobbyRoomTopic) -> Unit)>? = null

    private var item: LobbyRoomTopic? = null

    constructor(imagesLoader: ImagesLoader, itemView: View, dateManager: DateManager) : super(itemView) {
        this.imagesLoader = imagesLoader
        ButterKnife.bind(this, itemView)
        this.dateManager = dateManager
    }

    override fun init(item: LobbyRoomTopic) {
        this.item = item

        userNameView.text = item.creator.name
        dateView.text = dateManager.getRelativeTimeSpanString(item.creationDate)
        titleView.text = item.title
        contentView.text = item.description

        if(item.img != null) {
            imagesLoader.load(item.img!!, avatarView)
        }
    }

    fun setReplyListener(listener: ((item: LobbyRoomTopic) -> Unit)?) {
        if(listener == null) {
            return
        }

        replyListener = WeakReference(listener)
    }

    fun setUserClickistener(listener: ((item: LobbyRoomTopic) -> Unit)?) {
        if(listener == null) {
            return
        }

        avatarClickListener = WeakReference(listener)
    }

    @OnClick(R.id.cell_lobby_topic_reply)
    fun onReplyClick() {
        replyListener?.get()?.invoke(item!!)
    }

    @OnClick(R.id.cell_lobby_topic_avatar, R.id.cell_lobby_topic_user_name)
    fun onAvatarClick() {
        avatarClickListener?.get()?.invoke(item!!)
    }
}