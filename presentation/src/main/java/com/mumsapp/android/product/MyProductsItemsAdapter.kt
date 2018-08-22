package com.mumsapp.android.product

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseRecyclerViewAdapter
import com.mumsapp.android.shop.ReadableShopProduct
import com.mumsapp.android.util.ImagesLoader
import com.mumsapp.domain.model.shop.Product
import com.mumsapp.domain.repository.ResourceRepository
import javax.inject.Inject

class MyProductsItemsAdapter : BaseRecyclerViewAdapter<ReadableShopProduct, MyProductsViewHolder> {

    private val imagesLoader: ImagesLoader
    private val resourceRepository: ResourceRepository

    var edditButtonClickListener: ((item: ReadableShopProduct) -> Unit)? = null

    @Inject
    constructor(imagesLoader: ImagesLoader, resourceRepository: ResourceRepository) {
        this.imagesLoader = imagesLoader
        this.resourceRepository = resourceRepository
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyProductsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cell_my_products, parent, false)
        return MyProductsViewHolder(imagesLoader, resourceRepository, itemView)
    }

    override fun onBindViewHolder(holder: MyProductsViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        if(edditButtonClickListener != null) {
            holder.setEditButtonClickListener(edditButtonClickListener!!)
        }
    }
}