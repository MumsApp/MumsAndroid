package com.mumsapp.domain.model.user

import com.google.gson.annotations.SerializedName
import com.mumsapp.domain.model.BaseRequest

data class ChildRequest(@SerializedName("user_id") var userId: Int?,
                   @SerializedName("child_id") var childId: Int?,
                   @SerializedName("age") var age: Int,
                   @SerializedName("ageUnit") var ageUnit: Int,
                   @SerializedName("sex") var sex: Int)
    : BaseRequest()