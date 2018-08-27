package com.mumsapp.domain.model.shop

import com.mumsapp.domain.model.BaseRequest
import java.io.File

class UpdateShopProductRequest(var id: Int, var name: String, var description: String, var price: Float,
                                var categoryId: Int, var latitude: Double, var longitude: Double,
                                var locationName: String, var photos: List<File>?) : BaseRequest()