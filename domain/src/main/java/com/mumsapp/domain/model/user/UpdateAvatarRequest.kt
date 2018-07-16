package com.mumsapp.domain.model.user

import com.google.gson.annotations.SerializedName
import com.mumsapp.domain.model.BaseRequest
import java.io.File

data class UpdateAvatarRequest(@SerializedName("user_id") var userId: Int,
                               @SerializedName("file") var file: File) : BaseRequest()