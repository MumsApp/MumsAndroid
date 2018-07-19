package com.mumsapp.android.lobby

import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnCheckedChanged
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseViewHolder
import com.mumsapp.android.ui.views.BaseCheckbox
import com.mumsapp.android.ui.views.BaseSwitch
import com.mumsapp.android.ui.views.BaseTextView
import com.mumsapp.android.ui.views.CircleImageView
import com.mumsapp.android.util.ImagesLoader
import com.mumsapp.domain.model.lobby.LobbyRoom
import com.mumsapp.domain.repository.ImagesRepository
import java.lang.ref.WeakReference

class LobbyViewHolder : BaseViewHolder<LobbyRoom> {

    private val imagesLoader: ImagesLoader

    private val imagesRepository: ImagesRepository

    @BindView(R.id.lobby_cell_image)
    lateinit var imageView: CircleImageView

    @BindView(R.id.lobby_cell_title)
    lateinit var titleView: BaseTextView

    @BindView(R.id.lobby_cell_description)
    lateinit var descriptionView: BaseTextView

    @BindView(R.id.lobby_cell_checkbox)
    lateinit var switchView: BaseCheckbox

    private var listener: WeakReference<((item: LobbyRoom, value: Boolean) -> Unit)>? = null

    private var item: LobbyRoom? = null


    constructor(imagesLoader: ImagesLoader, itemView: View, imagesRepository: ImagesRepository) : super(itemView) {
        this.imagesLoader = imagesLoader
        this.imagesRepository = imagesRepository
        ButterKnife.bind(this, itemView)
    }

    override fun init(item: LobbyRoom) {
        this.item = item

        titleView.text = item.title
        descriptionView.text = item.description
        switchView.isChecked = item.isFavourite

        val url = imagesRepository.getApiImageUrl(item.imagePath)
        imagesLoader.load(url, imageView)
    }

    fun setCheckedListener(listener: (item: LobbyRoom, value: Boolean) -> Unit) {
        this.listener = WeakReference(listener)
    }

    @OnCheckedChanged(R.id.lobby_cell_checkbox)
    fun onCheckedChanged(value: Boolean) {
        listener?.get()?.invoke(item!!, value)
    }
}