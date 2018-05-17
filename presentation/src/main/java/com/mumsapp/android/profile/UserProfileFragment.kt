package com.mumsapp.android.profile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseFragment
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.base.LifecycleView
import com.mumsapp.android.common.dialogs.ConfirmationWithAvatarDialog
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.navigation.DialogsProvider
import com.mumsapp.android.ui.views.BaseTextView
import com.mumsapp.android.ui.views.CardsTextView
import com.mumsapp.android.ui.views.CircleImageView
import com.mumsapp.android.ui.views.TopBar
import com.mumsapp.android.ui.widgets.LocationWidget
import com.mumsapp.android.util.ImagesLoader
import javax.inject.Inject

class UserProfileFragment : BaseFragment(), UserProfileView {

    @Inject
    lateinit var presenter: UserProfilePresenter

    @Inject
    lateinit var imagesLoader: ImagesLoader

    @Inject
    lateinit var dialogsProvider: DialogsProvider

    @BindView(R.id.user_profile_top_bar)
    lateinit var topBar: TopBar

    @BindView(R.id.user_profile_avatar)
    lateinit var avatarView: CircleImageView

    @BindView(R.id.user_profile_name)
    lateinit var nameView: BaseTextView

    @BindView(R.id.user_profile_description)
    lateinit var descriptionView: BaseTextView

    @BindView(R.id.user_profile_kids_value)
    lateinit var kidsView: CardsTextView

    @BindView(R.id.user_profile_location_widget)
    lateinit var locationWidget: LocationWidget

    private var confirmationDialog: ConfirmationWithAvatarDialog? = null

    override fun <T : LifecyclePresenter<LifecycleView>> getLifecyclePresenter() = presenter as T

    companion object {
        fun getInstance(): UserProfileFragment {
            return UserProfileFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent(ActivityComponent::class.java)?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_user_profile, container, false)
        setUnbinder(ButterKnife.bind(this, v))
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachViewWithLifecycle(this)
        topBar.setLeftButtonClickListener { presenter.onBackClick() }
    }

    @OnClick(R.id.user_profile_remove_contact)
    fun onRemoveContactClick() {
        presenter.onRemoveContactClick()
    }

    override fun showProfileInfo(name: String, description: String) {
        nameView.text = name
        descriptionView.text = description
    }

    override fun loadAvatar(url: String) {
        imagesLoader.load(url, avatarView)
    }

    override fun showNumberOfKids(text: String) {
        kidsView.setText(text)
    }

    override fun showLocation(latitude: String, longitude: String, name: String) {
        locationWidget.setMapCoordinates(latitude, longitude)
        locationWidget.setLocationName(name)
    }

    override fun showRemoveUserDialog(avatarUri: Uri?, name: String, title: String, description: String,
                                      confirmButtonText: String, cancelButtonText: String,
                                      confirmationListener: () -> Unit, cancelListener: () -> Unit) {
        if(confirmationDialog == null) {
            confirmationDialog = dialogsProvider.createConfirmationWithAvatarDialog()
        }

        confirmationDialog?.show(avatarUri, name, title, description, confirmButtonText,
                cancelButtonText, confirmationListener, cancelListener)
    }
}