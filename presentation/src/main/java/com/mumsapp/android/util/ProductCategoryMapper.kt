package com.mumsapp.android.util

import com.mumsapp.android.shop.SelectProductCategoryItem
import com.mumsapp.android.shop.VIEW_TYPE_HEADER
import com.mumsapp.android.shop.VIEW_TYPE_ITEM
import com.mumsapp.domain.model.product.ProductCategoriesResponse
import com.mumsapp.domain.model.product.ProductCategory
import com.mumsapp.domain.model.product.ProductSubcategory
import javax.inject.Inject

class ProductCategoryMapper {

    @Inject
    constructor()

    fun map(response: ProductCategoriesResponse): List<SelectProductCategoryItem> {
        val items = ArrayList<SelectProductCategoryItem>()

        for(category: ProductCategory in response.data) {
            items.add(SelectProductCategoryItem(category.id, category.name, VIEW_TYPE_HEADER))

            for(subcategory: ProductSubcategory in category.subcategories) {
                items.add(SelectProductCategoryItem(subcategory.id, subcategory.name, VIEW_TYPE_ITEM))
            }
        }

        return items
    }
}