package com.mumsapp.data.repository

import com.mumsapp.android.data.R
import com.mumsapp.domain.exceptions.InvalidRefreshTokenException
import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.model.identity.RefreshTokenRequest
import com.mumsapp.domain.model.identity.Token
import com.mumsapp.domain.model.user.*
import com.mumsapp.domain.net.PublicRestApi
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.repository.UserRepository
import com.mumsapp.domain.utils.ExceptionDispatcher
import com.mumsapp.domain.utils.SerializationHelper
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class UserRepositoryImpl : BaseRestRepository, UserRepository {


    private val restApi: PublicRestApi

    @Inject
    constructor(restApi: PublicRestApi, exceptionDispatcher: ExceptionDispatcher,
                serializationHelper: SerializationHelper,
                resourceRepository: ResourceRepository) : super(exceptionDispatcher, serializationHelper, resourceRepository) {
        this.restApi = restApi
    }

    override fun createUser(request: SignUpRequest): Observable<EmptyResponse> {
        return requestWithErrorMapping(restApi.createUser(request))
    }

    override fun requestToken(request: SignInRequest): Observable<Token> {
        return requestWithErrorMapping(restApi.loginCheck(request))
    }

    override fun refreshToken(request: RefreshTokenRequest): Observable<Token> {
        return restApi.tokenRefresh(request)
                .onErrorResumeNext(Function { throwable ->
                    if (exceptionDispatcher.isUnAuthorized(throwable)) {
                        val errorMessage = resourceRepository.getString(R.string.error_refresh_token_invalid_or_expired)
                        return@Function Observable.error(InvalidRefreshTokenException(errorMessage))
                    }

                    return@Function Observable.error(throwable)
                })
    }

    override fun signUpFacebook(request: FacebookSignUpRequest): Observable<EmptyResponse> {
        return requestWithErrorMapping(restApi.facebookCreate(request))
    }

    override fun signInFacebook(request: FacebookSignInRequest): Observable<Token> {
        return requestWithErrorMapping(restApi.facebookLogin(request))
    }

    override fun signUpGoogle(request: GoogleSignUpRequest): Observable<EmptyResponse> {
        return requestWithErrorMapping(restApi.googleCreate(request))
    }

    override fun signInGoogle(request: GoogleSignInRequest): Observable<Token> {
        return requestWithErrorMapping(restApi.googleLogin(request))
    }

    override fun getUserData(id: Int, level: Int): Observable<UserResponse> {
        return requestWithErrorMapping(restApi.getUserData(id, level))
    }

    override fun updateUserLocation(id: Int, request: UpdateLocationRequest): Observable<EmptyResponse> {
        return requestWithErrorMapping(restApi.putUserLocation(id, request))
    }

    override fun updateUserDetails(id: Int, request: UpdateUserDetailsRequest): Observable<EmptyResponse> {
        return requestWithErrorMapping(restApi.putUser(id, request))
    }

    override fun createChild(userId: Int, request: ChildRequest): Observable<UserResponse> {
        return requestWithErrorMapping(restApi.postUserChild(userId, request))
    }

    override fun updateChild(userId: Int, childId: Int, request: ChildRequest): Observable<UserResponse> {
        return requestWithErrorMapping(restApi.putUserChild(userId, childId, request))
    }

    override fun deleteChild(userId: Int, childId: Int): Observable<EmptyResponse> {
        return requestWithErrorMapping(restApi.deleteUserChild(userId, childId))
    }

    override fun updateAvatar(userId: Int, file: File): Observable<EmptyResponse> {
        val filePart = MultipartBody.Part.createFormData("file", file.name, RequestBody.create(MediaType.parse("image/*"), file))
        return requestWithErrorMapping(restApi.postUserPhoto(userId, filePart))
    }
}