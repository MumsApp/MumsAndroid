package com.mumsapp.android.ui.widgets.members

import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseViewHolder
import com.mumsapp.android.ui.views.BaseTextView
import com.mumsapp.android.ui.views.CircleImageView
import com.mumsapp.android.util.ImagesLoader
import com.mumsapp.domain.model.chat.TemplateChatRecipient

class MembersWidgetViewHolder : BaseViewHolder<TemplateChatRecipient> {

    private val imagesLoader: ImagesLoader

    @BindView(R.id.cell_members_widget_avatar)
    lateinit var imageView: CircleImageView

    @BindView(R.id.cell_members_widget_text)
    lateinit var textView: BaseTextView

    constructor(imagesLoader: ImagesLoader, itemView: View) : super(itemView) {
        this.imagesLoader = imagesLoader
        ButterKnife.bind(this, itemView)
    }

    override fun init(item: TemplateChatRecipient) {
        imagesLoader.load(item.getAvatar(), imageView)
        textView.text = ""
    }

    fun init(text: String) {
        imageView.setImageResource(R.drawable.ic_avatar_placeholder_small)
        textView.text = text
    }
}