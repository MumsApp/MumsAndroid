package com.mumsapp.android.lobby

import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnCheckedChanged
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseViewHolder
import com.mumsapp.android.ui.views.BaseSwitch
import com.mumsapp.android.ui.views.BaseTextView
import com.mumsapp.android.ui.views.CircleImageView
import com.mumsapp.android.util.ImagesLoader
import com.mumsapp.domain.model.lobby.LobbyItem
import java.lang.ref.WeakReference

class LobbyViewHolder : BaseViewHolder<LobbyItem> {

    private val imagesLoader: ImagesLoader

    @BindView(R.id.lobby_cell_image)
    lateinit var imageView: CircleImageView

    @BindView(R.id.lobby_cell_title)
    lateinit var titleView: BaseTextView

    @BindView(R.id.lobby_cell_description)
    lateinit var descriptionView: BaseTextView

    @BindView(R.id.lobby_cell_switch)
    lateinit var switchView: BaseSwitch

    private var listener: WeakReference<((item: LobbyItem, value: Boolean) -> Unit)>? = null

    private var item: LobbyItem? = null


    constructor(imagesLoader: ImagesLoader, itemView: View) : super(itemView) {
        this.imagesLoader = imagesLoader
        ButterKnife.bind(this, itemView)
    }

    override fun init(item: LobbyItem) {
        this.item = item

        titleView.text = item.name
        descriptionView.text = item.description
        switchView.isChecked = item.joined
    }

    fun setCheckedListener(listener: (item: LobbyItem, value: Boolean) -> Unit) {
        this.listener = WeakReference(listener)
    }

    @OnCheckedChanged(R.id.lobby_cell_switch)
    fun onCheckedChanged(value: Boolean) {
        if(listener != null && listener!!.get() != null && item != null) {
            listener!!.get()!!.invoke(item!!, value)
        }
    }
}