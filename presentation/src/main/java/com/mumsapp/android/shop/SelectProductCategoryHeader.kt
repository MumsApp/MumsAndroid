package com.mumsapp.android.shop

import com.brandongogetap.stickyheaders.exposed.StickyHeader
import com.mumsapp.domain.model.shop.ProductSubcategory

class SelectProductCategoryHeader(subcategory: ProductSubcategory?, text: String, viewType: Int) :
        SelectProductCategoryItem(subcategory, text, viewType), StickyHeader