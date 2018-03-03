package com.mumsapp.android.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseFragment
import com.mumsapp.android.base.BasePresenter
import com.mumsapp.android.base.BaseView
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.di.scopes.ActivityScope
import com.mumsapp.android.ui.dialogs.AccountSettingsDialog
import com.mumsapp.android.ui.views.BaseTextView
import com.mumsapp.android.ui.views.CircleImageView
import com.mumsapp.android.ui.views.TopBar
import com.mumsapp.android.util.ImagesLoader
import javax.inject.Inject

class MyProfileFragment: BaseFragment(), MyProfileView {

    @Inject
    lateinit var presenter: MyProfilePresenter

    @Inject
    lateinit var imagesLoader: ImagesLoader

    @Inject
    lateinit var accountSettingsDialog: AccountSettingsDialog

    @BindView(R.id.my_profile_top_bar)
    lateinit var topBar: TopBar

    @BindView(R.id.my_profile_name)
    lateinit var nameView: BaseTextView

    @BindView(R.id.my_profile_description)
    lateinit var descriptionView: BaseTextView

    @BindView(R.id.my_profile_avatar)
    lateinit var avatarView: CircleImageView

    override fun <T : BasePresenter<BaseView>> getPresenter(): T = presenter as T

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
        topBar.setRightButtonClickListener( View.OnClickListener { presenter.onSettingsClick() })
    }

    override fun showProfileInfo(name: String, description: String) {
        nameView.text = name
        descriptionView.text = description
    }

    override fun loadAvatar(url: String) {
        imagesLoader.load(url, avatarView)
    }

    override fun showAccountSettingsDialog() {
        accountSettingsDialog.show()
    }
}