package com.mumsapp.android.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseRecyclerViewAdapter
import com.mumsapp.android.util.ImagesLoader
import com.mumsapp.domain.model.product.Product
import com.mumsapp.domain.repository.ResourceRepository
import javax.inject.Inject

class ShopItemsAdapter : BaseRecyclerViewAdapter<Product, ShopViewHolder> {

    private val imagesLoader: ImagesLoader
    private val resourceRepository: ResourceRepository

    var checkboxChangeListener: ((item: Product, value: Boolean) -> Unit)? = null

    @Inject
    constructor(imagesLoader: ImagesLoader, resourceRepository: ResourceRepository) {
        this.imagesLoader = imagesLoader
        this.resourceRepository = resourceRepository
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cell_shop, parent, false)
        return ShopViewHolder(imagesLoader, resourceRepository, itemView)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        if(checkboxChangeListener != null) {
            holder.setCheckedListener(checkboxChangeListener!!)
        }
    }
}