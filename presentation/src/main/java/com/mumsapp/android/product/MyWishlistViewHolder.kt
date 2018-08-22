package com.mumsapp.android.product

import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnCheckedChanged
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseViewHolder
import com.mumsapp.android.shop.ReadableShopProduct
import com.mumsapp.android.ui.views.BaseImageView
import com.mumsapp.android.ui.views.BaseTextView
import com.mumsapp.android.ui.views.CircleImageView
import com.mumsapp.android.util.ImagesLoader
import com.mumsapp.domain.model.shop.Product
import com.mumsapp.domain.repository.ResourceRepository
import java.lang.ref.WeakReference

class MyWishlistViewHolder : BaseViewHolder<ReadableShopProduct> {

    private val imagesLoader: ImagesLoader
    private val resourceRepository: ResourceRepository

    @BindView(R.id.my_wishlist_cell_image)
    lateinit var productImage: BaseImageView

    @BindView(R.id.my_wishlist_cell_distance)
    lateinit var distanceView: BaseTextView

    @BindView(R.id.my_wishlist_cell_product_name)
    lateinit var productNameView: BaseTextView

    @BindView(R.id.my_wishlist_cell_category)
    lateinit var categoryView: BaseTextView

    @BindView(R.id.my_wishlist_cell_price)
    lateinit var priceView: BaseTextView

    @BindView(R.id.my_wishlist_cell_avatar)
    lateinit var avatarImage: CircleImageView

    @BindView(R.id.my_wishlist_cell_user_name)
    lateinit var userNameView: BaseTextView

    private var listener: WeakReference<((item: ReadableShopProduct, value: Boolean) -> Unit)>? = null

    private var item: ReadableShopProduct? = null

    constructor(imagesLoader: ImagesLoader, resourceRepository: ResourceRepository, itemView: View) : super(itemView) {
        this.imagesLoader = imagesLoader
        this.resourceRepository = resourceRepository
        ButterKnife.bind(this, itemView)
    }

    override fun init(item: ReadableShopProduct) {
        this.item = item

        distanceView.text = item.distance
        productNameView.text = item.name
        categoryView.text = item.categoryName
        userNameView.text = item.authorName
        priceView.text = item.price

        if(item.thumbnailPath != null) {
            imagesLoader.loadFromApiPath(item.thumbnailPath!!, productImage)
        }

        if(item.authorAvatarPath != null) {
            imagesLoader.loadFromApiPath(item.authorAvatarPath!!, avatarImage)
        }
    }

    fun setCheckedListener(listener: (item: ReadableShopProduct, value: Boolean) -> Unit) {
        this.listener = WeakReference(listener)
    }

    @OnCheckedChanged(R.id.my_wishlist_cell_checkbox)
    fun onCheckedChanged(value: Boolean) {
        listener?.get()?.invoke(item!!, value)
    }
}