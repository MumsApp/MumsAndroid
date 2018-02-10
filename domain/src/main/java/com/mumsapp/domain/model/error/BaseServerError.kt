package com.mumsapp.domain.model.error

import com.google.gson.annotations.SerializedName
import java.security.MessageDigest

class BaseServerError {

    @SerializedName("status")
    lateinit var status: String

    @SerializedName("message")
    lateinit var message: String

    @SerializedName("exceptionName")
    lateinit var exceptionName: String
}