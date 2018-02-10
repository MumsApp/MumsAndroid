package com.mumsapp.domain.net

import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.model.user.SignUpRequest
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PublicRestApi {

    @POST("user/create")
    fun createUser(@Body params: SignUpRequest): Observable<Response<EmptyResponse>>
}