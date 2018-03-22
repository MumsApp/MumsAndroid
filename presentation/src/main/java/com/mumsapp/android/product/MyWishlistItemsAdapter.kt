package com.mumsapp.android.product

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseRecyclerViewAdapter
import com.mumsapp.android.util.ImagesLoader
import com.mumsapp.domain.model.product.ProductItem
import com.mumsapp.domain.repository.ResourceRepository
import javax.inject.Inject

class MyWishlistItemsAdapter : BaseRecyclerViewAdapter<ProductItem, MyWishlistViewHolder> {

    private val imagesLoader: ImagesLoader
    private val resourceRepository: ResourceRepository

    var checkboxChangeListener: ((item: ProductItem, value: Boolean) -> Unit)? = null

    @Inject
    constructor(imagesLoader: ImagesLoader, resourceRepository: ResourceRepository) {
        this.imagesLoader = imagesLoader
        this.resourceRepository = resourceRepository
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyWishlistViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cell_my_wishlist, parent, false)
        return MyWishlistViewHolder(imagesLoader, resourceRepository, itemView)
    }

    override fun onBindViewHolder(holder: MyWishlistViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        if(checkboxChangeListener != null) {
            holder.setCheckedListener(checkboxChangeListener!!)
        }
    }
}