package com.mumsapp.android.chat

import android.view.View
import android.widget.CheckBox
import butterknife.BindView
import butterknife.ButterKnife
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseViewHolder
import com.mumsapp.android.ui.views.BaseTextView
import com.mumsapp.domain.model.chat.TemplateChatRecipient
import de.hdodenhof.circleimageview.CircleImageView

class ChatSettingsViewHolder : BaseViewHolder<TemplateChatRecipient> {

    @BindView(R.id.chat_settings_cell_image)
    lateinit var image: CircleImageView

    @BindView(R.id.chat_settings_cell_text)
    lateinit var name: BaseTextView

    @BindView(R.id.chat_settings_cell_checkbox)
    lateinit var checkbox: CheckBox

    constructor(itemView: View) : super(itemView) {
        ButterKnife.bind(this, itemView)
    }

    override fun init(item: TemplateChatRecipient) {
        name.text = item.name
    }
}