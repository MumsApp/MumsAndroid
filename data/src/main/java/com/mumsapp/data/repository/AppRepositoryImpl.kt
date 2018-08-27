package com.mumsapp.data.repository

import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.model.lobby.*
import com.mumsapp.domain.model.shop.*
import com.mumsapp.domain.net.PublicRestApi
import com.mumsapp.domain.repository.AppRepository
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.utils.ExceptionDispatcher
import com.mumsapp.domain.utils.SerializationHelper
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class AppRepositoryImpl : BaseRestRepository, AppRepository {

    private val restApi: PublicRestApi

    @Inject
    constructor(restApi: PublicRestApi, exceptionDispatcher: ExceptionDispatcher,
                serializationHelper: SerializationHelper,
                resourceRepository: ResourceRepository) : super(exceptionDispatcher, serializationHelper, resourceRepository) {
        this.restApi = restApi
    }

    override fun getLobbyRooms(page: Int, perPage: Int): Observable<LobbyResponse> {
        return requestWithErrorMapping(restApi.getLobbyRoomPage(page, perPage))
    }

    override fun searchLobbyRooms(request: SearchLobbyRequest, page: Int, perPage: Int): Observable<LobbyResponse> {
        return requestWithErrorMapping(restApi.getLobbyRoomSearch(page, perPage, request.searchTerm,
                request.withDescription))
    }

    override fun addLobbyToFavourite(id: Int): Observable<EmptyResponse> {
        return requestWithErrorMapping(restApi.postLobbyRoomIdFavourite(id))
    }

    override fun removeLobbyFromFavourite(id: Int): Observable<EmptyResponse> {
        return requestWithErrorMapping(restApi.deleteLobbyRoomIdFavourite(id))
    }

    override fun joinLobbyRoom(id: Int): Observable<EmptyResponse> {
        return requestWithErrorMapping(restApi.postLobbyRoomIdJoin(id))
    }

    override fun leaveLobbyRoom(id: Int): Observable<EmptyResponse> {
        return requestWithErrorMapping(restApi.deleteLobbyRoomIdJoin(id))
    }

    override fun createLobbyRoom(request: CreateLobbyRoomRequest): Observable<LobbyRoomResponse> {
        val filePart = MultipartBody.Part.createFormData("file", request.file.name,
                RequestBody.create(MediaType.parse("image/*"), request.file))
        val apiRequest = restApi.postLobbyRoom(request.title, request.description, request.public,
                filePart)

        return requestWithErrorMapping(apiRequest)
    }

    override fun deleteLobbyRoom(id: Int): Observable<EmptyResponse> {
        return requestWithErrorMapping(restApi.deleteLobbyRoomId(id))
    }

    override fun getLobbyRoomTopics(lobbyRoomId: Int, page: Int, perPage: Int): Observable<LobbyRoomTopicsResponse> {
        return requestWithErrorMapping(restApi.getLobbyRoomIdTopics(lobbyRoomId, page, perPage))
    }

    override fun createLobbyRoomTopic(request: CreateLobbyRoomTopicRequest): Observable<EmptyResponse> {
        var apiRequest: Observable<Response<EmptyResponse>>? = null
        apiRequest = if (request.file == null) {
            restApi.postLobbyRoomTopic(request.roomId, request.title,
                    request.description)
        } else {
            val filePart = MultipartBody.Part.createFormData("file", request.file!!.name,
                    RequestBody.create(MediaType.parse("image/*"), request.file!!))
            restApi.postLobbyRoomTopicMultipart(request.roomId, request.title,
                    request.description, filePart)
        }

        return requestWithErrorMapping(apiRequest)
    }

    override fun getLobbyRoomTopicPosts(lobbyRoomId: Int, lobbyRoomTopicId: Int, page: Int, perPage: Int): Observable<LobbyRoomTopicPostsResponse> {
        return requestWithErrorMapping(restApi.getLobbyRoomIdTopicIdPost(lobbyRoomId, lobbyRoomTopicId,
                page, perPage))
    }

    override fun createLobbyTopicPost(request: CreateLobbyTopicPostRequest): Observable<EmptyResponse> {
        return requestWithErrorMapping(restApi.postLobbyRoomTopicPost(request.roomId, request.topicId,
                request))
    }

    override fun getProductCategories(): Observable<ProductCategoriesResponse> {
        return requestWithErrorMapping(restApi.getShopCategory())
    }

    override fun searchShopProducts(request: SearchShopRequest, page: Int, perPage: Int): Observable<ProductsResponse> {
        val apiRequest = restApi.getShopProductSearch(page, perPage, request.searchTerm,
                request.categoryId, request.priceFrom, request.priceTo, request.userLat,
                request.userLon, request.distanceFrom, request.distanceTo)

        return requestWithErrorMapping(apiRequest)
    }

    override fun addProductToFavourite(id: Int): Observable<EmptyResponse> {
        return requestWithErrorMapping(restApi.postShopProductIdFavourite(id))
    }

    override fun removeProductFromFavourite(id: Int): Observable<EmptyResponse> {
        return requestWithErrorMapping(restApi.deleteShopProductIdFavourite(id))
    }

    override fun getProductDetails(id: Int): Observable<ProductResponse> {
        return requestWithErrorMapping(restApi.getShopProductId(id))
    }

    override fun createShopProduct(request: CreateShopProductRequest): Observable<ProductResponse> {
        val files = ArrayList<MultipartBody.Part>()

        request.photos.forEachIndexed { i: Int, file: File ->
            val filePart = MultipartBody.Part.createFormData("file$i", file.name,
                    RequestBody.create(MediaType.parse("image/*"), file))
            files.add(filePart)
        }

        val apiRequest = restApi.postShopProduct(request.name, request.description, request.price,
                request.categoryId, request.latitude, request.longitude, request.locationName, files)

        return requestWithErrorMapping(apiRequest)
    }

    override fun updateShopProduct(request: UpdateShopProductRequest): Observable<EmptyResponse> {
        val files = ArrayList<MultipartBody.Part>()

        request.photos?.forEachIndexed { i: Int, file: File ->
            val filePart = MultipartBody.Part.createFormData("file$i", file.name,
                    RequestBody.create(MediaType.parse("image/*"), file))
            files.add(filePart)
        }

        val apiRequest = restApi.putShopProduct(request.id, request.name, request.description, request.price,
                request.categoryId, request.latitude, request.longitude, request.locationName, files)

        return requestWithErrorMapping(apiRequest)
    }

    override fun getMyProducts(): Observable<ProductsMyResponse> {
        return requestWithErrorMapping(restApi.getShopProductMy())
    }

    override fun getFavouriteProducts(): Observable<ProductsMyResponse> {
        return requestWithErrorMapping(restApi.getShopProductFavourite())
    }
}