package com.mumsapp.android.shop

import com.mumsapp.domain.model.BaseResponse
import com.mumsapp.domain.model.shop.ProductSubcategory

const val VIEW_TYPE_HEADER = 1
const val VIEW_TYPE_ITEM = 2

open class SelectProductCategoryItem(var subcategory: ProductSubcategory?, var text: String, var viewType: Int): BaseResponse()