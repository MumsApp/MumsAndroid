package com.mumsapp.domain.model.user

import com.google.gson.annotations.SerializedName
import com.mumsapp.domain.model.BaseResponse

data class UserResponse(@SerializedName("status") var status: String,
                        @SerializedName("data") var data: User) : BaseResponse() {

    data class User(@SerializedName("id") var id: Int,
                    @SerializedName("name") var firstName: String,
                    @SerializedName("surname") var lastName: String,
                    @SerializedName("description") var description: String?,
                    @SerializedName("children") var children: List<Child>,
                    @SerializedName("photo") var photo: Photo,
                    @SerializedName("location") var location: Location) : BaseResponse()

    data class Child(@SerializedName("id") var id: Int?,
                     @SerializedName("age") var age: Int?,
                     @SerializedName("ageUnit") var ageUnit: Int?,
                     @SerializedName("sex") var sex: Int?) : BaseResponse()

    data class Photo(@SerializedName("src") var src: String?) : BaseResponse()

    data class Location(@SerializedName("name") var name: String?,
                        @SerializedName("placeID") var placeId: String?,
                        @SerializedName("lat") var latitude: String?,
                        @SerializedName("lon") var longitude: String?,
                        @SerializedName("formattedAddress") var formattedAddress: String?,
                        @SerializedName("enabled") var enabled: Boolean?)
        : BaseResponse()
}