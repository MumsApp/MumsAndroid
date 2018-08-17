package com.mumsapp.domain.interactor.shop

import com.mumsapp.domain.model.BaseRequest

data class ShopFavouriteRequest(var productId: Int) : BaseRequest()