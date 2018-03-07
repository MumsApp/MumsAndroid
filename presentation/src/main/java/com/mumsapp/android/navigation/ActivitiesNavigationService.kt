package com.mumsapp.android.navigation

import android.content.Intent
import android.net.Uri
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.mumsapp.android.authentication.AuthActivity
import com.mumsapp.android.base.BaseActivity
import com.mumsapp.android.base.LifecycleFragment
import com.mumsapp.android.main.MainActivity
import javax.inject.Inject

class ActivitiesNavigationService {

    private val activity: BaseActivity

    @Inject
    constructor(activity: BaseActivity) {
        this.activity = activity
    }

    fun openAuthActivity() {
        val intent = AuthActivity.createIntent(activity)
        activity.startActivity(intent)
    }

    fun openMainActivity() {
        val intent = MainActivity.createIntent(activity)
        activity.startActivity(intent)
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

    fun openGooglePlacesOverlayActivity(fragment: LifecycleFragment, requestCode: Int) {
        val intent = createGooglePlacesOverlayActivityIntent()
        fragment.startActivityForResult(intent, requestCode)
    }

    fun createGooglePlacesOverlayActivityIntent(): Intent {
        return PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(activity)
    }
}