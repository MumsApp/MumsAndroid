package com.mumsapp.domain.interactor.user

import com.mumsapp.domain.model.BaseRequest

data class UserFriendRequest(var friendId: Int) : BaseRequest()