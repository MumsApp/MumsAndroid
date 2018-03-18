package com.mumsapp.domain.model.product

import com.mumsapp.domain.model.BaseResponse

data class TemplateProductResponse(var items: List<ProductItem>) : BaseResponse()

data class ProductItem(var id: Int, var name: String, var category: String,
                       var price: Double, var distance: String, var userName: String) : BaseResponse()