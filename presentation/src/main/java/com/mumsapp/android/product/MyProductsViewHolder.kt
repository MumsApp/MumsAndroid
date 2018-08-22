package com.mumsapp.android.product

import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseViewHolder
import com.mumsapp.android.shop.ReadableShopProduct
import com.mumsapp.android.ui.views.BaseImageView
import com.mumsapp.android.ui.views.BaseTextView
import com.mumsapp.android.util.ImagesLoader
import com.mumsapp.domain.model.shop.Product
import com.mumsapp.domain.repository.ResourceRepository
import java.lang.ref.WeakReference

class MyProductsViewHolder : BaseViewHolder<ReadableShopProduct> {

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

    private var listener: WeakReference<((item: ReadableShopProduct) -> Unit)>? = null

    private var item: ReadableShopProduct? = null

    constructor(imagesLoader: ImagesLoader, resourceRepository: ResourceRepository, itemView: View) : super(itemView) {
        this.imagesLoader = imagesLoader
        this.resourceRepository = resourceRepository
        ButterKnife.bind(this, itemView)
    }

    override fun init(item: ReadableShopProduct) {
        this.item = item

        nameView.text = item.name
        categoryView.text = item.categoryName
        priceView.text = item.price
    }

    fun setEditButtonClickListener(listener: (item: ReadableShopProduct) -> Unit) {
        this.listener = WeakReference(listener)
    }

    @OnClick(R.id.my_products_cell_edit_button)
    fun onEditClick() {
        listener?.get()?.invoke(item!!)
    }
}