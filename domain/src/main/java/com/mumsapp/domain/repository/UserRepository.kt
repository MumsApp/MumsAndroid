package com.mumsapp.domain.repository

import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.model.identity.RefreshTokenRequest
import com.mumsapp.domain.model.identity.Token
import com.mumsapp.domain.model.user.*
import io.reactivex.Observable
import java.io.File
import java.util.*

interface UserRepository {

    fun createUser(request: SignUpRequest): Observable<EmptyResponse>

    fun requestToken(request: SignInRequest): Observable<Token>

    fun refreshToken(request: RefreshTokenRequest): Observable<Token>

    fun signUpFacebook(request: FacebookSignUpRequest): Observable<EmptyResponse>

    fun signInFacebook(request: FacebookSignInRequest): Observable<Token>

    fun signUpGoogle(request: GoogleSignUpRequest): Observable<EmptyResponse>

    fun signInGoogle(request: GoogleSignInRequest): Observable<Token>

    fun getUserData(id: Int, level: Int): Observable<UserResponse>

    fun updateUserLocation(id: Int, request: UpdateLocationRequest): Observable<EmptyResponse>

    fun updateUserDetails(id: Int, request: UpdateUserDetailsRequest): Observable<EmptyResponse>

    fun createChild(userId: Int, request: ChildRequest): Observable<UserResponse>

    fun updateChild(userId: Int, childId: Int, request: ChildRequest): Observable<UserResponse>

    fun deleteChild(userId: Int, childId: Int): Observable<EmptyResponse>

    fun updateAvatar(userId: Int, file: File): Observable<EmptyResponse>

    fun addUserFriend(friendId: Int): Observable<EmptyResponse>

    fun removeUserFriend(friendId: Int): Observable<EmptyResponse>
}