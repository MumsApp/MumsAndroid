package com.mumsapp.android.lobby

import android.support.constraint.ConstraintLayout
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseViewHolder
import com.mumsapp.android.ui.views.BaseTextView
import com.mumsapp.android.util.ImagesLoader
import com.mumsapp.domain.model.lobby.LobbyRoomTopic
import com.mumsapp.domain.model.lobby.LobbyRoomTopicPost
import com.mumsapp.domain.utils.DateManager
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.ref.WeakReference

class LobbyRoomTopicPostViewHolder : BaseViewHolder<LobbyRoomTopicPost> {

    private val imagesLoader: ImagesLoader
    private val dateManager: DateManager

    @BindView(R.id.cell_lobby_post_root_View)
    lateinit var rootView: ConstraintLayout

    @BindView(R.id.cell_lobby_post_avatar)
    lateinit var avatarView: CircleImageView

    @BindView(R.id.cell_lobby_post_user_name)
    lateinit var userNameView: BaseTextView

    @BindView(R.id.cell_lobby_post_date)
    lateinit var dateView: BaseTextView

    @BindView(R.id.cell_lobby_post_content)
    lateinit var contentView: BaseTextView

    private var avatarClickListener: WeakReference<((item: LobbyRoomTopicPost) -> Unit)>? = null

    private var item: LobbyRoomTopicPost? = null

    constructor(imagesLoader: ImagesLoader, itemView: View, dateManager: DateManager) : super(itemView) {
        this.imagesLoader = imagesLoader
        ButterKnife.bind(this, itemView)
        this.dateManager = dateManager
    }

    override fun init(item: LobbyRoomTopicPost) {
        this.item = item

        userNameView.text = item.author.name
        dateView.text = dateManager.getRelativeTimeSpanString(item.creationDate)
        contentView.text = item.description

        if(item.author.imgPath != null) {
            imagesLoader.loadFromApiPath(item.author.imgPath!!, avatarView)
        }
    }

    fun setRootBackground(color: Int) {
        rootView.setBackgroundColor(color)
    }

    fun setUserClickListener(listener: ((item: LobbyRoomTopicPost) -> Unit)?) {
        if(listener == null) {
            return
        }

        avatarClickListener = WeakReference(listener)
    }

    @OnClick(R.id.cell_lobby_post_avatar, R.id.cell_lobby_post_user_name)
    fun onAvatarClick() {
        avatarClickListener?.get()?.invoke(item!!)
    }
}