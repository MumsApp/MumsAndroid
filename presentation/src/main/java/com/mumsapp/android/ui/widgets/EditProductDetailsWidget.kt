package com.mumsapp.android.ui.widgets

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.mumsapp.android.R
import com.mumsapp.android.ui.views.BaseEditText
import com.mumsapp.android.ui.views.BaseInput
import com.mumsapp.android.ui.views.BaseTextView

class EditProductDetailsWidget : CardView {

    @BindView(R.id.edit_product_details_title)
    lateinit var titleInput: BaseInput

    @BindView(R.id.edit_product_details_category)
    lateinit var categoryView: BaseTextView

    @BindView(R.id.edit_product_details_ask_for_category)
    lateinit var askForCategoryView: BaseTextView

    @BindView(R.id.edit_product_details_price)
    lateinit var priceInput: BaseInput

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
        descriptionInput.enableScroll()
    }

    fun setTitle(title: String?) {
        titleInput.text = title
    }

    fun getTitle() = titleInput.text

    fun setCategory(category: String?) {
        categoryView.text = category
    }

    fun getCategory() = categoryView.text

    fun setPrice(price: String?) {
        priceInput.text = price
    }

    fun getPrice() = priceInput.text

    fun setDescription(description: String?) {
        descriptionInput.setText(description)
    }

    fun getDescription() = descriptionInput.text

    fun setOnAddCategoryClickListener(listener: (v: View) -> Unit) {
        categoryView.setOnClickListener(listener)
    }

    fun setOnAskForCategoryClickListener(listener: (v: View) -> Unit) {
        askForCategoryView.setOnClickListener(listener)
    }
}