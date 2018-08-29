package com.mumsapp.domain.net

import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.model.identity.RefreshTokenRequest
import com.mumsapp.domain.model.identity.Token
import com.mumsapp.domain.model.lobby.*
import com.mumsapp.domain.model.shop.ProductCategoriesResponse
import com.mumsapp.domain.model.shop.ProductResponse
import com.mumsapp.domain.model.shop.ProductsMyResponse
import com.mumsapp.domain.model.shop.ProductsResponse
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
    fun postUserChild(@Path("user_id") userId: Int, @Body param: ChildRequest): Observable<Response<UserResponse>>

    @PUT("user/{user_id}/child/{child_id}")
    fun putUserChild(@Path("user_id") userId: Int, @Path("child_id") childId: Int, @Body param: ChildRequest): Observable<Response<UserResponse>>

    @DELETE("user/{user_id}/child/{child_id}")
    fun deleteUserChild(@Path("user_id") userId: Int, @Path("child_id") childId: Int): Observable<Response<EmptyResponse>>

    @Multipart
    @POST("user/{user_id}/photo")
    fun postUserPhoto(@Path("user_id") userId: Int, @Part filePart: MultipartBody.Part): Observable<Response<EmptyResponse>>

    @POST("user/friend/{friendId}")
    fun postUserFriend(@Path("friendId") friendId: Int): Observable<Response<EmptyResponse>>

    @DELETE("user/friend/{friendId}")
    fun deleteUserFriend(@Path("friendId") friendId: Int): Observable<Response<EmptyResponse>>

    @GET("lobby/room/page/{page}/{perPage}")
    fun getLobbyRoomPage(@Path("page") page: Int, @Path("perPage") perPage: Int): Observable<Response<LobbyResponse>>

    @GET("lobby/room/page/{page}/{perPage}")
    fun getLobbyRoomSearch(@Path("page") page: Int, @Path("perPage") perPage: Int,
                           @Query("searchTerm") searchTerm: String,
                           @Query("withDescription") withDescription: Boolean): Observable<Response<LobbyResponse>>

    @POST("lobby/room/{id}/favourite")
    fun postLobbyRoomIdFavourite(@Path("id") id: Int): Observable<Response<EmptyResponse>>

    @DELETE("lobby/room/{id}/favourite")
    fun deleteLobbyRoomIdFavourite(@Path("id") id: Int): Observable<Response<EmptyResponse>>

    @POST("lobby/room/{id}/join")
    fun postLobbyRoomIdJoin(@Path("id") id: Int): Observable<Response<EmptyResponse>>

    @DELETE("lobby/room/{id}/join")
    fun deleteLobbyRoomIdJoin(@Path("id") id: Int): Observable<Response<EmptyResponse>>

    @Multipart
    @POST("lobby/room")
    fun postLobbyRoom(@Query("title") title: String,
                      @Query("description") description: String,
                      @Query("public") public: Boolean, @Part filePart: MultipartBody.Part): Observable<Response<LobbyRoomResponse>>

    @DELETE("lobby/room/{id}")
    fun deleteLobbyRoomId(@Path("id") id: Int): Observable<Response<EmptyResponse>>

    @GET("lobby/room/{roomId}/topic/page/{page}/{perPage}")
    fun getLobbyRoomIdTopics(@Path("roomId") lobbyRoomId: Int, @Path("page") page: Int,
                             @Path("perPage") perPage: Int): Observable<Response<LobbyRoomTopicsResponse>>

    @Multipart
    @POST("lobby/room/{roomId}/topic")
    fun postLobbyRoomTopicMultipart(@Path("roomId") lobbyRoomId: Int, @Part("title") title: String,
                                    @Part("description") description: String, @Part filePart: MultipartBody.Part?): Observable<Response<EmptyResponse>>

    @Multipart
    @POST("lobby/room/{roomId}/topic")
    fun postLobbyRoomTopic(@Path("roomId") lobbyRoomId: Int, @Part("title") title: String,
                           @Part("description") description: String): Observable<Response<EmptyResponse>>

    @GET("lobby/room/{roomId}/topic/{topicId}/post/page/{page}/{perPage}")
    fun getLobbyRoomIdTopicIdPost(@Path("roomId") lobbyRoomId: Int, @Path("topicId") topicId: Int,
                                  @Path("page") page: Int, @Path("perPage") perPage: Int): Observable<Response<LobbyRoomTopicPostsResponse>>

    @POST("lobby/room/{roomId}/topic/{topicId}/post")
    fun postLobbyRoomTopicPost(@Path("roomId") lobbyRoomId: Int, @Path("topicId") topicId: Int,
                               @Body request: CreateLobbyTopicPostRequest): Observable<Response<EmptyResponse>>

    @GET("shop/category")
    fun getShopCategory(): Observable<Response<ProductCategoriesResponse>>

    @GET("shop/product/search/{page}/{perPage}")
    fun getShopProductSearch(@Path("page") page: Int, @Path("perPage") perPage: Int,
                             @Query("searchTerm") searchTerm: String?,
                             @Query("category") category: Int?,
                             @Query("priceFrom") priceFrom: Float?,
                             @Query("priceTo") priceTo: Float?,
                             @Query("userLat") userLat: Double?,
                             @Query("userLon") userLon: Double?,
                             @Query("distanceFrom") distanceFrom: Int?,
                             @Query("distanceTo") distanceTo: Int?): Observable<Response<ProductsResponse>>

    @POST("shop/product/{id}/favourite")
    fun postShopProductIdFavourite(@Path("id") productId: Int): Observable<Response<EmptyResponse>>

    @DELETE("shop/product/{id}/favourite")
    fun deleteShopProductIdFavourite(@Path("id") productId: Int): Observable<Response<EmptyResponse>>

    @GET("shop/product/{id}")
    fun getShopProductId(@Path("id") productId: Int): Observable<Response<ProductResponse>>

    @Multipart
    @POST("shop/product")
    fun postShopProduct(@Query("name") name: String, @Query("description") description: String,
                        @Query("price") price: Float, @Query("category") categoryId: Int,
                        @Query("lat") latitude: Double, @Query("lon") longitude: Double,
                        @Query("pointName") pointName: String, @Part filePart: List<MultipartBody.Part>): Observable<Response<ProductResponse>>

    @Multipart
    @PUT("shop/product/{id}")
    fun putShopProduct(@Path("id") productId: Int, @Query("name") name: String,
                        @Query("description") description: String,
                        @Query("price") price: Float, @Query("category") categoryId: Int,
                        @Query("lat") latitude: Double, @Query("lon") longitude: Double,
                        @Query("pointName") pointName: String, @Part filePart: List<MultipartBody.Part>): Observable<Response<EmptyResponse>>

    @PUT("shop/product/{id}")
    fun putShopProduct(@Path("id") productId: Int, @Query("name") name: String,
                       @Query("description") description: String,
                       @Query("price") price: Float, @Query("category") categoryId: Int,
                       @Query("lat") latitude: Double, @Query("lon") longitude: Double,
                       @Query("pointName") pointName: String): Observable<Response<EmptyResponse>>

    @GET("shop/product/my")
    fun getShopProductMy(): Observable<Response<ProductsMyResponse>>

    @GET("shop/product/favourite")
    fun getShopProductFavourite(): Observable<Response<ProductsMyResponse>>

    @DELETE("shop/product/{productId}/photo/{photoId}")
    fun deleteShopProductIdPhotoId(@Path("productId") productId: Int, @Path("photoId") photoId: Int): Observable<Response<EmptyResponse>>

    @PUT("shop/product/{productId}/photo/{photoId}/thumbnail")
    fun putShopProductIdPhotoIdThumbnail(@Path("productId") productId: Int, @Path("photoId") photoId: Int): Observable<Response<EmptyResponse>>
}