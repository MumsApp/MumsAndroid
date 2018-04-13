package com.mumsapp.android.product

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseRecyclerViewAdapter
import com.mumsapp.android.util.ImagesLoader
import javax.inject.Inject

class ProductImagesAdapter : BaseRecyclerViewAdapter<ImageSliderItem, ProductImagesViewHolder> {

    private val TYPE_ADD_PHOTO = 1
    private val TYPE_IMAGE = 2

    private val imagesLoader: ImagesLoader

    var deleteButtonClickListener: ((position: Int) -> Unit)? = null

    @Inject
    constructor(imagesLoader: ImagesLoader) {
        this.imagesLoader = imagesLoader
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_ADD_PHOTO else TYPE_IMAGE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductImagesViewHolder {

        return when (viewType) {
            TYPE_ADD_PHOTO -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cell_product_photos_add_photo, parent, false)
                ProductAddPhotoViewHolder(imagesLoader, itemView)
            }

            TYPE_IMAGE -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cell_product_photos_photo, parent, false)
                ProductImagesViewHolder(imagesLoader, itemView)
            }

            else -> {
                throw IllegalStateException("Wrong view type")
            }
        }

    }

    override fun onBindViewHolder(holder: ProductImagesViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        if (deleteButtonClickListener != null) {
            holder.setDeleteButtonClickListener(deleteButtonClickListener!!)
        }
    }
}