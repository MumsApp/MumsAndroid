package com.mumsapp.android.profile

import android.content.Context
import android.os.Bundle
import butterknife.BindView
import butterknife.ButterKnife
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseDialog
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.base.LifecycleView
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.ui.views.TopBar
import javax.inject.Inject

class UserDetailsSettingsDialog(context: Context) : BaseDialog(context), UserDetailsSettingsView {

    @Inject
    lateinit var presenter: UserDetailsSettingsPresenter

    @BindView(R.id.user_details_settings_top_bar)
    lateinit var topBar: TopBar

    override fun <T : LifecyclePresenter<LifecycleView>> getPresenter() = presenter as T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    private fun setup() {
        getComponent(ActivityComponent::class.java)?.inject(this)
        setContentView(R.layout.dialog_user_details_settings)
        configureWindow()
        setUnbinder(ButterKnife.bind(this))
        topBar.setRightButtonClickListener {
            presenter.onCloseClick()
        }
        presenter.attachView(this)
    }

    override fun dismissView() {
        dismiss()
    }
}