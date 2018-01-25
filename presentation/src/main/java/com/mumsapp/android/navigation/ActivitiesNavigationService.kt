package com.mumsapp.android.navigation

import android.content.Intent
import android.net.Uri
import com.mumsapp.android.base.BaseActivity
import javax.inject.Inject

class ActivitiesNavigationService {

    private val activity: BaseActivity

    @Inject
    constructor(activity: BaseActivity) {
        this.activity = activity
    }

    fun openWebsiteActivity(url: String) {
        val intent = this.createWebsiteActivity(url)
        this.activity.startActivity(intent)
    }

    fun createWebsiteActivity(url: String): Intent {
        return Intent(Intent.ACTION_VIEW, Uri.parse(url))
    }

    fun finishCurrentActivity() {
        this.activity.finish()
    }
}