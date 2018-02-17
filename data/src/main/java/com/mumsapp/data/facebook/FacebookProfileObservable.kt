package com.mumsapp.data.facebook

import android.os.Bundle
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.GraphResponse
import com.mumsapp.domain.model.user.FacebookUserResponse
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import org.json.JSONObject

class FacebookProfileObservable(private val accessToken: AccessToken) : ObservableOnSubscribe<FacebookUserResponse> {


    override fun subscribe(emitter: ObservableEmitter<FacebookUserResponse>) {
        val params = Bundle()
        params.putString("fields", "first_name,last_name,email,picture.width(640).height(640)")
        val request = GraphRequest.newMeRequest(accessToken, { jsonObject: JSONObject, graphResponse: GraphResponse ->
            val email = jsonObject.getString("email")
            val firstName = jsonObject.getString("first_name")
            val lastName = jsonObject.getString("last_name")
            val user = FacebookUserResponse(firstName, lastName, email, accessToken.token)
            emitter.onNext(user)
            emitter.onComplete()
        })
        request.parameters = params
        request.executeAsync()
    }
}