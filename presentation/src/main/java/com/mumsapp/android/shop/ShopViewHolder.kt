package com.mumsapp.android.shop

import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnCheckedChanged
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseViewHolder
import com.mumsapp.android.ui.views.BaseTextView
import com.mumsapp.android.ui.views.CircleImageView
import com.mumsapp.android.ui.views.RoundedCornersImageView
import com.mumsapp.android.util.ImagesLoader
import com.mumsapp.domain.model.product.ProductItem
import com.mumsapp.domain.repository.ResourceRepository
import kotlinx.android.synthetic.main.cell_shop.view.*
import java.lang.ref.WeakReference

class ShopViewHolder : BaseViewHolder<ProductItem> {

    private val imagesLoader: ImagesLoader

    private val resourceRepository: ResourceRepository

    @BindView(R.id.shop_cell_image)
    lateinit var imageView: RoundedCornersImageView

    @BindView(R.id.shop_cell_name)
    lateinit var nameView: BaseTextView

    @BindView(R.id.shop_cell_category)
    lateinit var categoryView: BaseTextView

    @BindView(R.id.shop_cell_price)
    lateinit var priceView: BaseTextView

    @BindView(R.id.shop_cell_distance)
    lateinit var distanceView: BaseTextView

    @BindView(R.id.shop_cell_avatar)
    lateinit var avatarView: CircleImageView

    @BindView(R.id.shop_cell_user_name)
    lateinit var userNameView: BaseTextView

    private var listener: WeakReference<((item: ProductItem, value: Boolean) -> Unit)>? = null

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
        priceView.text = resourceRepository.getString(R.string.pounds_price_format, item.price)
        distanceView.text = item.distance
        userNameView.text = item.userName
    }

    fun setCheckedListener(listener: (item: ProductItem, value: Boolean) -> Unit) {
        this.listener = WeakReference(listener)
    }

    @OnCheckedChanged(R.id.shop_cell_favourite_checkbox)
    fun onCheckedChanged(value: Boolean) {
        if(listener != null && listener!!.get() != null && item != null) {
            listener!!.get()!!.invoke(item!!, value)
        }
    }
}