package com.mumsapp.android.product

import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseViewHolder
import com.mumsapp.android.ui.views.BaseImageView
import com.mumsapp.android.ui.views.BaseTextView
import com.mumsapp.android.ui.views.CircleImageView
import com.mumsapp.android.util.ImagesLoader
import com.mumsapp.domain.model.product.ProductItem
import com.mumsapp.domain.repository.ResourceRepository

class MyWishlistViewHolder : BaseViewHolder<ProductItem> {

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

    constructor(imagesLoader: ImagesLoader, resourceRepository: ResourceRepository, itemView: View) : super(itemView) {
        this.imagesLoader = imagesLoader
        this.resourceRepository = resourceRepository
        ButterKnife.bind(this, itemView)
    }

    override fun init(item: ProductItem) {
        distanceView.text = item.distance
        productNameView.text = item.name
        categoryView.text = item.category
        userNameView.text = item.userName

        val price = resourceRepository.getString(R.string.pounds_price_format, item.price)
        priceView.text = price
    }
}