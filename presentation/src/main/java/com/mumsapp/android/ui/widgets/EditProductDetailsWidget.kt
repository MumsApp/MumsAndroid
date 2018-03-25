package com.mumsapp.android.ui.widgets

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.mumsapp.android.R
import com.mumsapp.android.ui.views.BaseEditText

class EditProductDetailsWidget : CardView {

    @BindView(R.id.edit_product_details_description)
    lateinit var descriptionInput: BaseEditText

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
        val view = View.inflate(context, R.layout.widget_edit_product_details, this)
        ButterKnife.bind(view)
        descriptionInput.enableSccroll()
    }
}