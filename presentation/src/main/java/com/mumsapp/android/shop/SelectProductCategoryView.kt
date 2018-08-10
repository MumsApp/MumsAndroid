package com.mumsapp.android.shop

import com.mumsapp.android.base.LifecycleView

interface SelectProductCategoryView : LifecycleView {

    fun showCategories(list: List<SelectProductCategoryItem>,
                       itemsClickListener: (item: SelectProductCategoryItem) -> Unit)
}