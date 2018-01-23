package com.mumsapp.android.authentication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mumsapp.android.R
import javax.inject.Inject

class AuthActivity : AppCompatActivity() {

    @Inject
    lateinit var presenter: AuthPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }
}
