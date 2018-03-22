package com.mumsapp.android.product

import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseViewHolder
import com.mumsapp.android.ui.views.BaseImageView
import com.mumsapp.android.ui.views.BaseTextView
import com.mumsapp.android.util.ImagesLoader
import com.mumsapp.domain.model.product.ProductItem
import com.mumsapp.domain.repository.ResourceRepository
import java.lang.ref.WeakReference

class MyProductsViewHolder : BaseViewHolder<ProductItem> {

    private val imagesLoader: ImagesLoader

    private val resourceRepository: ResourceRepository

    @BindView(R.id.my_products_cell_image)
    lateinit var imageView: BaseImageView

    @BindView(R.id.my_products_cell_name)
    lateinit var nameView: BaseTextView

    @BindView(R.id.my_products_cell_category)
    lateinit var categoryView: BaseTextView

    @BindView(R.id.my_products_cell_price)
    lateinit var priceView: BaseTextView

    private var listener: WeakReference<((item: ProductItem) -> Unit)>? = null

    private var item: ProductItem? = null

    constructor(imagesLoader: ImagesLoader, resourceRepository: ResourceRepository, itemView: View) : super(itemView) {
        this.imagesLoader = imagesLoader
        this.resourceRepository = resourceRepository
        ButterKnife.bind(this, itemView)
    }

    override fun init(item: ProductItem) {
        this.item = item

        nameView.text = item.name
        categoryView.text = item.category

        val price = resourceRepository.getString(R.string.pounds_price_format, item.price)
        priceView.text = price
    }

    fun setEditButtonClickListener(listener: (item: ProductItem) -> Unit) {
        this.listener = WeakReference(listener)
    }

    @OnClick(R.id.my_products_cell_edit_button)
    fun onEdditClick() {
        if(listener != null && listener!!.get() != null && item != null) {
            listener!!.get()!!.invoke(item!!)
        }
    }
}