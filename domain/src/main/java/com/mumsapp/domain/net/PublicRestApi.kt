package com.mumsapp.domain.net

import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.model.identity.RefreshTokenRequest
import com.mumsapp.domain.model.identity.Token
import com.mumsapp.domain.model.lobby.LobbyResponse
import com.mumsapp.domain.model.user.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface PublicRestApi {

    @POST("public/user/create")
    fun createUser(@Body params: SignUpRequest): Observable<Response<EmptyResponse>>

    @POST("login_check")
    fun loginCheck(@Body param: SignInRequest): Observable<Response<Token>>

    @POST("token/refresh")
    fun tokenRefresh(@Body param: RefreshTokenRequest): Observable<Token>

    @POST("public/facebook/create")
    fun facebookCreate(@Body param: FacebookSignUpRequest): Observable<Response<EmptyResponse>>

    @POST("public/facebook/login")
    fun facebookLogin(@Body param: FacebookSignInRequest): Observable<Response<Token>>

    @POST("public/google/create")
    fun googleCreate(@Body param: GoogleSignUpRequest): Observable<Response<EmptyResponse>>

    @POST("public/google/login")
    fun googleLogin(@Body param: GoogleSignInRequest): Observable<Response<Token>>

    @GET("user/{id}/{level}")
    fun getUserData(@Path("id") id: Int, @Path("level") level: Int): Observable<Response<UserResponse>>

    @PUT("user/{id}/location")
    fun putUserLocation(@Path("id") id: Int, @Body param: UpdateLocationRequest): Observable<Response<EmptyResponse>>

    @PUT("user/{id}")
    fun putUser(@Path("id") id: Int, @Body param: UpdateUserDetailsRequest): Observable<Response<EmptyResponse>>

    @POST("user/{user_id}/child")
    fun postUserChild(@Path("user_id") userId: Int, @Body param: ChildRequest): Observable<Response<EmptyResponse>>

    @PUT("user/{user_id}/child/{child_id}")
    fun putUserChild(@Path("user_id") userId: Int, @Path("child_id") childId: Int, @Body param: ChildRequest): Observable<Response<EmptyResponse>>

    @DELETE("user/{user_id}/child/{child_id}")
    fun deleteUserChild(@Path("user_id") userId: Int, @Path("child_id") childId: Int): Observable<Response<EmptyResponse>>

    @Multipart
    @POST("user/{user_id}/photo")
    fun postUserPhoto(@Path("user_id") userId: Int, @Part filePart: MultipartBody.Part): Observable<Response<EmptyResponse>>

    @GET("lobby/room/page/{page}/{perPage}")
    fun getLobbyRoomPage(@Path("page") page: Int, @Path("perPage") perPage: Int): Observable<Response<LobbyResponse>>

    @GET("lobby/room/page/{page}/{perPage}")
    fun getLobbyRoomSearch(@Path("page") page: Int, @Path("perPage") perPage: Int,
                           @Query("searchTerm") searchTerm: String,
                           @Query("withDescription") withDescription: Boolean): Observable<Response<LobbyResponse>>
}