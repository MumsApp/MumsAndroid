package com.mumsapp.android.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseFragment
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.base.LifecycleView
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.navigation.ActivitiesNavigationService
import com.mumsapp.android.navigation.DialogsProvider
import com.mumsapp.android.ui.views.BaseTextView
import com.mumsapp.android.ui.views.CircleImageView
import com.mumsapp.android.ui.views.LocationWidget
import com.mumsapp.android.ui.views.TopBar
import com.mumsapp.android.util.ImagesLoader
import javax.inject.Inject

class MyProfileFragment: BaseFragment(), MyProfileView {

    @Inject
    lateinit var presenter: MyProfilePresenter

    @Inject
    lateinit var imagesLoader: ImagesLoader

    @Inject
    lateinit var dialogsProvider: DialogsProvider

    @Inject
    lateinit var activitiesNavigationService: ActivitiesNavigationService

    @BindView(R.id.my_profile_top_bar)
    lateinit var topBar: TopBar

    @BindView(R.id.my_profile_name)
    lateinit var nameView: BaseTextView

    @BindView(R.id.my_profile_description)
    lateinit var descriptionView: BaseTextView

    @BindView(R.id.my_profile_avatar)
    lateinit var avatarView: CircleImageView

    @BindView(R.id.my_profile_location_widget)
    lateinit var locationWidget: LocationWidget

    private var accountSettingsDialog: AccountSettingsDialog? = null

    private var userDetailsSettingsDialog: UserDetailsSettingsDialog? = null

    override fun <T : LifecyclePresenter<LifecycleView>> getPresenter(): T = presenter as T

    companion object {
        fun getInstance(): MyProfileFragment {
            return MyProfileFragment()
        }

        private val GOOGLE_PLACES_REQUEST_CODE = 1;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent(ActivityComponent::class.java)?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_my_profile, container, false)
        setUnbinder(ButterKnife.bind(this, v))
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachViewWithLifecycle(this)
        topBar.setRightButtonClickListener { presenter.onSettingsClick() }
        configureLocationWidget()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GOOGLE_PLACES_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                val place = PlaceAutocomplete.getPlace(context, data)
                presenter.onLocationSelected(place)
            } else if(resultCode == PlaceAutocomplete.RESULT_ERROR) {
                val status = PlaceAutocomplete.getStatus(context, data)
                presenter.onLocationError(status)
            }
        }
    }

    override fun showProfileInfo(name: String, description: String) {
        nameView.text = name
        descriptionView.text = description
    }

    override fun loadAvatar(url: String) {
        imagesLoader.load(url, avatarView)
    }

    @OnClick(R.id.my_profile_change_name)
    fun onChangeClick() {
        presenter.onChangeClick()
    }

    override fun showAccountSettingsDialog() {
        if(accountSettingsDialog == null) {
            accountSettingsDialog = dialogsProvider.createAccountSettingsDialog()
        }
        accountSettingsDialog?.show()
    }

    override fun showUserDetailsSettingsDialog(firstName: String?, lastName: String?, description: String?,
                                               listener: (firstName: String, lastName: String, description: String) -> Unit) {
        if(userDetailsSettingsDialog == null) {
            userDetailsSettingsDialog = dialogsProvider.createUserDetailsSettingsDialog()
        }

        userDetailsSettingsDialog?.show(firstName, lastName, description, listener)
    }

    private fun configureLocationWidget() {
        locationWidget.setWidgetButtonListener {
            presenter.onEditLocationClickListener()
        }

        locationWidget.setSwitchChangeListener( { _: CompoundButton, value: Boolean ->
            presenter.onLocationSwitchChanged(value)
        })
    }

    override fun showEditLocationScreen() {
        activitiesNavigationService.openGooglePlacesOverlayActivity(this, GOOGLE_PLACES_REQUEST_CODE)
    }

    override fun showLocation() {
        locationWidget.setMapVisibility(true)
        locationWidget.setSwitchValue(true)
    }

    override fun showNewLocation(latitude: String, longitude: String, name: String) {
        showLocation()
        locationWidget.setMapCoordinates(latitude, longitude)
        locationWidget.setLocationName(name)
    }

    override fun hideLocation() {
        locationWidget.setMapVisibility(false)
    }
}