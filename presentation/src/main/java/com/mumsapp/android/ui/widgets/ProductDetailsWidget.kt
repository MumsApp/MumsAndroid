package com.mumsapp.android.ui.widgets

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.mumsapp.android.R
import com.mumsapp.android.common.features.HasComponent
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.ui.views.BaseButton
import com.mumsapp.android.ui.views.BaseTextView
import com.mumsapp.android.ui.views.CircleImageView
import com.mumsapp.android.util.ImagesLoader
import javax.inject.Inject

class ProductDetailsWidget : CardView {

    @Inject
    lateinit var imagesLoader: ImagesLoader

    @BindView(R.id.product_details_name)
    lateinit var productNameView: BaseTextView

    @BindView(R.id.product_details_category)
    lateinit var categoryView: BaseTextView

    @BindView(R.id.product_details_avatar)
    lateinit var avatarView: CircleImageView

    @BindView(R.id.product_details_user_name)
    lateinit var userNameView: BaseTextView

    @BindView(R.id.product_details_price)
    lateinit var priceView: BaseTextView

    @BindView(R.id.product_details_distance)
    lateinit var distanceView: BaseTextView

    @BindView(R.id.product_details_description)
    lateinit var descriptionView: BaseTextView

    @BindView(R.id.product_details_contact_button)
    lateinit var contactButton: BaseButton

    constructor(context: Context) : super(context) {
        setup(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setup(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setup(context, attrs)
    }

    private fun setup(context: Context, attrs: AttributeSet?) {
        val view = View.inflate(context, R.layout.widget_product_details, this)
        ButterKnife.bind(view)

        if(context is HasComponent<*>) {
            (context as HasComponent<ActivityComponent>).getComponent().inject(this)
        }
    }

    fun setProductName(productName: String) {
        productNameView.text = productName
    }

    fun setCategory(category: String) {
        categoryView.text = category
    }

    fun setAvatar(avatarUrl: String?) {
        if(avatarUrl != null) {
            imagesLoader.loadFromApiPath(avatarUrl, avatarView)
        }
    }

    fun setUserName(userName: String) {
        userNameView.text = userName
    }

    fun setPrice(price: String) {
        priceView.text = price
    }

    fun setDistance(distance: String?) {
        distanceView.text = distance
    }

    fun setDescription(description: String) {
        descriptionView.text = description
    }

    fun setUserClickListener(listener: (v: View) -> Unit) {
        userNameView.setOnClickListener(listener)
        avatarView.setOnClickListener(listener)
    }

    fun setContactButtonListener(listener: (v: View) -> Unit) {
        contactButton.setOnClickListener(listener)
    }
}