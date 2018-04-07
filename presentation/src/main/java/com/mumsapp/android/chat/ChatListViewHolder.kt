package com.mumsapp.android.chat

import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseViewHolder
import com.mumsapp.android.ui.views.BaseImageView
import com.mumsapp.android.ui.views.BaseTextView
import com.mumsapp.android.ui.views.CircleImageView
import com.mumsapp.android.util.ImagesLoader
import com.mumsapp.domain.model.chat.TemplateChatThread

class ChatListViewHolder : BaseViewHolder<TemplateChatThread> {

    private val imagesLoader: ImagesLoader

    @BindView(R.id.chat_list_cell_image)
    lateinit var avatarView: CircleImageView

    @BindView(R.id.chat_list_cell_online_status)
    lateinit var onlineStatusView: BaseImageView

    @BindView(R.id.chat_list_cell_verification_status)
    lateinit var verificationStatusView: BaseImageView

    @BindView(R.id.chat_list_cell_title)
    lateinit var titleView: BaseTextView

    @BindView(R.id.chat_list_cell_time)
    lateinit var timeView: BaseTextView

    @BindView(R.id.chat_list_cell_received_indicator)
    lateinit var receivedIndicatorView: BaseImageView

    @BindView(R.id.chat_list_cell_read_indicator)
    lateinit var readIndicatorView: BaseImageView

    @BindView(R.id.chat_list_cell_description)
    lateinit var descriptionView: BaseTextView

    constructor(imagesLoader: ImagesLoader, itemView: View) : super(itemView) {
        this.imagesLoader = imagesLoader
        ButterKnife.bind(this, itemView)
    }

    override fun init(item: TemplateChatThread) {
        titleView.text = item.recipient.name
        timeView.text = item.date
        descriptionView.text = item.messages[0].content
    }
}