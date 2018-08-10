package com.mumsapp.android.shop

import android.view.ViewGroup
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseRecyclerViewAdapter
import com.mumsapp.android.base.BaseViewHolder
import javax.inject.Inject

class SelectProductCategoryAdapter : BaseRecyclerViewAdapter<SelectProductCategoryItem, BaseViewHolder<SelectProductCategoryItem>> {

    @Inject
    constructor()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<SelectProductCategoryItem> {
        return when(viewType) {

            VIEW_TYPE_HEADER -> {
                val itemView = inflate(parent, R.layout.cell_select_product_category_header)
                SelectProductCategoryHeaderViewHolder(itemView)
            }

            VIEW_TYPE_ITEM -> {
                val itemView = inflate(parent, R.layout.cell_select_product_category_item)
                SelectProductCategoryItemViewHolder(itemView)
            }

            else -> {
                throw IllegalStateException("Wrong view type")
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }
}