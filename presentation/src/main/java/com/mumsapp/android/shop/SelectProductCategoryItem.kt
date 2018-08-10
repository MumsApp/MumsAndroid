package com.mumsapp.android.shop

import com.mumsapp.domain.model.BaseResponse

const val VIEW_TYPE_HEADER = 1
const val VIEW_TYPE_ITEM = 2

open class SelectProductCategoryItem(var id: Int, var text: String, var viewType: Int): BaseResponse()