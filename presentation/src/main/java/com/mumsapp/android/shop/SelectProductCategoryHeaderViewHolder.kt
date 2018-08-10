package com.mumsapp.android.shop

import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseViewHolder
import com.mumsapp.android.ui.views.BaseTextView

class SelectProductCategoryHeaderViewHolder : BaseViewHolder<SelectProductCategoryItem> {

    @BindView(R.id.cell_select_product_category_header_text)
    lateinit var textView: BaseTextView

    constructor(view: View) : super(view) {
        ButterKnife.bind(this, view)
    }

    override fun init(item: SelectProductCategoryItem) {
        textView.text = item.text
    }
}