package com.mumsapp.android.profile

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
import com.mumsapp.android.ui.views.CardTextView
import com.mumsapp.android.ui.views.CircleImageView
import com.mumsapp.android.ui.views.TopBar
import com.mumsapp.android.ui.widgets.LocationWidget
import com.mumsapp.android.util.ImagesLoader
import com.mumsapp.android.util.USER_ID_KEY
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
    lateinit var kidsView: CardTextView

    @BindView(R.id.user_profile_add_contact)
    lateinit var addContactButton: BaseTextView

    @BindView(R.id.user_profile_remove_contact)
    lateinit var removeContactButton: BaseTextView

    @BindView(R.id.user_profile_location_widget)
    lateinit var locationWidget: LocationWidget

    private var confirmationDialog: ConfirmationWithAvatarDialog? = null

    override fun <T : LifecyclePresenter<LifecycleView>> getLifecyclePresenter() = presenter as T

    companion object {
        fun getInstance(userId: Int): UserProfileFragment {
            val fragment = UserProfileFragment()
            fragment.arguments = createArgBundle(userId)

            return fragment
        }

        private fun createArgBundle(userId: Int): Bundle {
            val args = Bundle()
            args.putInt(USER_ID_KEY, userId)

            return args
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
        passArgumentsToPresenter()
        presenter.attachViewWithLifecycle(this)
        topBar.setLeftButtonClickListener { presenter.onBackClick() }
    }

    @OnClick(R.id.user_profile_add_contact)
    fun onAddContactClick() {
        presenter.onAddContactClick()
    }

    @OnClick(R.id.user_profile_remove_contact)
    fun onRemoveContactClick() {
        presenter.onRemoveContactClick()
    }

    override fun showProfileInfo(name: String, description: String?) {
        nameView.text = name
        descriptionView.text = description
    }

    override fun loadAvatar(url: String) {
        imagesLoader.loadFromApiPath(url, avatarView)
    }

    override fun showNumberOfKids(text: String) {
        kidsView.setText(text)
    }

    override fun showLocation(latitude: String, longitude: String, name: String) {
        locationWidget.setMapCoordinates(latitude, longitude)
        locationWidget.setLocationName(name)
    }

    override fun setAddContactVisibility(visible: Boolean) {
        setVisibilistyFromBoolean(visible, addContactButton)
    }

    override fun setRemoveContactVisibility(visible: Boolean) {
        setVisibilistyFromBoolean(visible, removeContactButton)
    }

    override fun showRemoveUserDialog(avatarPath: String?, name: String, title: String, description: String,
                                      confirmButtonText: String, cancelButtonText: String,
                                      confirmationListener: () -> Unit, cancelListener: () -> Unit) {
        if(confirmationDialog == null) {
            confirmationDialog = dialogsProvider.createConfirmationWithAvatarDialog()
        }

        confirmationDialog?.show(avatarPath, name, title, description, confirmButtonText,
                cancelButtonText, confirmationListener, cancelListener)
    }

    private fun passArgumentsToPresenter() {
        val userId = arguments!!.getInt(USER_ID_KEY)
        presenter.setArguments(userId)
    }
}