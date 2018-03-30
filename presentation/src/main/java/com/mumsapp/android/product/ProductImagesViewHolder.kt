package com.mumsapp.android.product

import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseViewHolder
import com.mumsapp.android.ui.views.BaseImageView
import com.mumsapp.android.util.ImagesLoader
import java.lang.ref.WeakReference

open class ProductImagesViewHolder : BaseViewHolder<ImageSliderItem> {

    private val imagesLoader: ImagesLoader

    @BindView(R.id.product_photos_cell_photo_image)
    lateinit var imageView: BaseImageView

    private var listener: WeakReference<((position: Int) -> Unit)>? = null

    constructor(imagesLoader: ImagesLoader, itemView: View) : super(itemView) {
        this.imagesLoader = imagesLoader
        bindButterknife()
    }

    override fun init(item: ImageSliderItem) {
        imagesLoader.load(item.uri!!, imageView, 0.5f)
    }

    fun setDeleteButtonClickListener(listener: (position: Int) -> Unit) {
        this.listener = WeakReference(listener)
    }

    @OnClick(R.id.product_photos_cell_delete_photo)
    fun onDeleteClick() {
        listener?.get()?.invoke(adapterPosition)
    }

    open fun bindButterknife() {
        ButterKnife.bind(this, itemView)
    }
}