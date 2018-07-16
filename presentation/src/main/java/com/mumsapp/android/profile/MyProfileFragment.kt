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
import com.mumsapp.android.common.dialogs.ConfirmationDialog
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.navigation.ActivitiesNavigationService
import com.mumsapp.android.navigation.DialogsProvider
import com.mumsapp.android.product.SelectImageSourceDialog
import com.mumsapp.android.ui.views.BaseTextView
import com.mumsapp.android.ui.views.CircleImageView
import com.mumsapp.android.ui.widgets.LocationWidget
import com.mumsapp.android.ui.views.TopBar
import com.mumsapp.android.ui.widgets.children_selection.ChildrenSelectionWidget
import com.mumsapp.android.ui.widgets.members.MembersWidget
import com.mumsapp.android.ui.widgets.mums_app_offers.MumsAppOffersWidget
import com.mumsapp.android.util.CAMERA_REQUEST_CODE
import com.mumsapp.android.util.GALLERY_REQUEST_CODE
import com.mumsapp.android.util.GOOGLE_PLACES_REQUEST_CODE
import com.mumsapp.android.util.ImagesLoader
import com.mumsapp.domain.model.chat.TemplateChatRecipient
import com.mumsapp.domain.model.mums_app_offers.TemplateMumsAppOffer
import com.mumsapp.domain.model.user.UserResponse
import javax.inject.Inject

class MyProfileFragment : BaseFragment(), MyProfileView {

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

    @BindView(R.id.my_profile_children_selection_widget)
    lateinit var childrenSelectionWidget: ChildrenSelectionWidget

    @BindView(R.id.my_profile_mums_app_offers_widget)
    lateinit var offersWidget: MumsAppOffersWidget

    @BindView(R.id.my_profile_friends)
    lateinit var membersWidget: MembersWidget

    private var accountSettingsDialog: AccountSettingsDialog? = null

    private var userDetailsSettingsDialog: UserDetailsSettingsDialog? = null

    private var addChildDialog: AddChildDialog? = null

    private var confirmationDialog: ConfirmationDialog? = null

    private var selectImageSourceDialog: SelectImageSourceDialog? = null

    override fun <T : LifecyclePresenter<LifecycleView>> getLifecyclePresenter() = presenter as T

    companion object {
        fun getInstance(): MyProfileFragment {
            return MyProfileFragment()
        }
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
        configureChildrenSelectionWidget()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_PLACES_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val place = PlaceAutocomplete.getPlace(context, data)
                presenter.onLocationSelected(place)
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                val status = PlaceAutocomplete.getStatus(context, data)
                presenter.onLocationError(status)
            }
        }

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    presenter.onGalleryImageReceived(data!!.data)
                }

                CAMERA_REQUEST_CODE -> {
                    presenter.onCameraImageReceived()
                }

                GOOGLE_PLACES_REQUEST_CODE -> {
                    val place = PlaceAutocomplete.getPlace(context, data)
                    presenter.onLocationSelected(place)
                }
            }
        } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
            if (resultCode == GOOGLE_PLACES_REQUEST_CODE) {
                val status = PlaceAutocomplete.getStatus(context, data)
                presenter.onLocationError(status)
            }
        }
    }

    override fun showProfileInfo(name: String, description: String?) {
        nameView.text = name
        descriptionView.text = description
    }

    override fun loadAvatar(url: String) {
        imagesLoader.load(url, avatarView)
    }

    @OnClick(R.id.my_profile_avatar)
    fun onAvatarClick() {
        presenter.onAvatarClick()
    }

    @OnClick(R.id.my_profile_change_name)
    fun onChangeClick() {
        presenter.onChangeClick()
    }

    override fun showAccountSettingsDialog() {
        if (accountSettingsDialog == null) {
            accountSettingsDialog = dialogsProvider.createAccountSettingsDialog()
        }
        accountSettingsDialog?.show()
    }

    override fun showUserDetailsSettingsDialog(firstName: String?, lastName: String?, description: String?,
                                               listener: (firstName: String, lastName: String, description: String) -> Unit) {
        if (userDetailsSettingsDialog == null) {
            userDetailsSettingsDialog = dialogsProvider.createUserDetailsSettingsDialog()
        }

        userDetailsSettingsDialog?.show(firstName, lastName, description, listener)
    }

    private fun configureLocationWidget() {
        locationWidget.setWidgetButtonListener {
            presenter.onEditLocationClick()
        }

        locationWidget.setSwitchChangeListener({ _: CompoundButton, value: Boolean ->
            presenter.onLocationSwitchChanged(value)
        })
    }

    private fun configureChildrenSelectionWidget() {
        childrenSelectionWidget.addMaleListener = presenter::onAddMaleClick
        childrenSelectionWidget.addFemaleListener = presenter::onAddFemaleClick
        childrenSelectionWidget.addToComeListener = presenter::onAddToComeClick
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
        locationWidget.setLocationName(null)
        locationWidget.setMapVisibility(false)
    }

    override fun showChildren(items: List<UserResponse.Child>, editListener: (item: UserResponse.Child) -> Unit,
                              deleteListener: (item: UserResponse.Child) -> Unit) {
        childrenSelectionWidget.showChildren(items, editListener, deleteListener)
    }

    override fun hideChildren() {
        childrenSelectionWidget.hideChildren()
    }

    override fun notifyChildAdded(items: List<UserResponse.Child>, position: Int) {
        childrenSelectionWidget.notifyChildAdded(items, position)
    }

    override fun notifyChildRemoved(items: List<UserResponse.Child>, position: Int) {
        childrenSelectionWidget.notifyChildRemoved(items, position)
    }

    override fun showAddChildDialog(sex: Int, selectedChild: UserResponse.Child?, actionListener: (child: UserResponse.Child) -> Unit) {
        if (addChildDialog == null) {
            addChildDialog = dialogsProvider.createAddChildDialog()
        }

        addChildDialog?.show(sex, selectedChild, actionListener)
    }

    override fun showOffers(offers: List<TemplateMumsAppOffer>) {
        offersWidget.setOffers(offers)
    }

    override fun showFriends(users: List<TemplateChatRecipient>) {
        membersWidget.setMembers(users)
    }

    override fun showConfirmationDialog(title: String, description: String, confirmButtonText: String, confirmButtonListener: () -> Unit) {
        if (confirmationDialog == null) {
            confirmationDialog = dialogsProvider.createConfirmationDialog()
        }

        confirmationDialog?.show(title, description, confirmButtonText, confirmButtonListener)
    }

    override fun showSelectImageSourceDialog(galleryClickListener: () -> Unit, cameraClickListener: () -> Unit) {
        if (selectImageSourceDialog == null) {
            selectImageSourceDialog = dialogsProvider.createSelectImageSourceDialog()
        }

        selectImageSourceDialog?.show(galleryClickListener, cameraClickListener)
    }
}