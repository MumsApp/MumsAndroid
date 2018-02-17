package com.mumsapp.domain.net

import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.model.identity.Token
import com.mumsapp.domain.model.user.*
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PublicRestApi {

    @POST("public/user/create")
    fun createUser(@Body params: SignUpRequest): Observable<Response<EmptyResponse>>

    @POST("login_check")
    fun loginCheck(@Body param: SignInRequest): Observable<Response<Token>>

    @POST("public/facebook/create")
    fun facebookCreate(@Body param: FacebookSignUpRequest): Observable<Response<EmptyResponse>>

    @POST("public/facebook/login")
    fun facebookLogin(@Body param: FacebookSignInRequest): Observable<Response<Token>>

    @POST("public/google/create")
    fun googleCreate(@Body param: GoogleSignUpRequest): Observable<Response<EmptyResponse>>

    @POST("public/google/login")
    fun googleLogin(@Body param: GoogleSignInRequest): Observable<Response<Token>>
}