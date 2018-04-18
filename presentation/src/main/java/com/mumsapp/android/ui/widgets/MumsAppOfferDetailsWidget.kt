package com.mumsapp.android.ui.widgets

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.mumsapp.android.R
import com.mumsapp.android.ui.views.BaseButton
import com.mumsapp.android.ui.views.BaseTextView

class MumsAppOfferDetailsWidget : CardView {

    @BindView(R.id.mums_app_offer_details_title)
    lateinit var titleView: BaseTextView

    @BindView(R.id.mums_app_offer_details_description)
    lateinit var descriptionView: BaseTextView

    @BindView(R.id.mums_app_offer_details_get_voucher)
    lateinit var getVoucherButton: BaseButton

    @BindView(R.id.mums_app_offer_details_follow)
    lateinit var followButton: BaseButton

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
        val view = View.inflate(context, R.layout.widget_mums_app_offer_details, this)
        ButterKnife.bind(view)
    }

    fun setTitle(title: String) {
        titleView.text = title
    }

    fun setDescription(description: String) {
        descriptionView.text = description
    }

    fun setGetVoucherButtonClickListener(listener: () -> Void) {
        getVoucherButton.setOnClickListener { listener.invoke() }
    }

    fun setFollowButtonClickListener(listener: () -> Void) {
        followButton.setOnClickListener { listener.invoke() }
    }
}