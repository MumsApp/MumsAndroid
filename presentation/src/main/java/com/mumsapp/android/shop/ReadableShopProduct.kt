package com.mumsapp.android.shop

import com.mumsapp.domain.model.BaseResponse
import com.mumsapp.domain.model.shop.Product

data class ReadableShopProduct(var product: Product, var id: Int, var name: String, var categoryName: String,
                               var price: String, var distance: String, var authorName: String,
                               var isFavourite: Boolean, var authorAvatarPath: String?,
                               var thumbnailPath: String?) : BaseResponse()