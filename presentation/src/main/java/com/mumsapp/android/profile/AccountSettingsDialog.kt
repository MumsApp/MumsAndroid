package com.mumsapp.android.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnCheckedChanged
import butterknife.OnClick
import com.mumsapp.android.R
import com.mumsapp.android.base.*
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.ui.views.BaseSwitch
import com.mumsapp.android.ui.views.NonCLickableFrameLayout
import com.mumsapp.android.ui.views.TopBar
import javax.inject.Inject

class AccountSettingsDialog(context: Context) : BaseDialog(context), AccountSettingsView {

    @Inject
    lateinit var presenter: AccountSettingsPresenter

    @BindView(R.id.account_settings_progress_layout)
    lateinit var progressView: NonCLickableFrameLayout

    @BindView(R.id.account_settings_top_bar)
    lateinit var topBar: TopBar

    @BindView(R.id.account_settings_emergency_button)
    lateinit var emergencySwitch: BaseSwitch

    override fun <T : BasePresenter<BaseView>> getPresenter(): T = presenter as T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    private fun setup() {
        getComponent(ActivityComponent::class.java)?.inject(this)
        setContentView(R.layout.dialog_account_settings)
        configureWindow()
        ButterKnife.bind(this)
        topBar.setRightButtonClickListener {
            presenter.onCloseClick()
        }
        presenter.attachView(this)
    }

    @OnCheckedChanged(R.id.account_settings_emergency_button)
    fun onEmergencyButtonValueChanged(value: Boolean) {
        presenter.onEmergencyButtonValueChanged(value)
    }

    @OnClick(R.id.account_settings_logout)
    fun onLogOutClick() {
        presenter.onLogOutClick()
    }

    override fun dismissView() {
        dismiss()
    }

    override fun setEmergencyButtonValue(value: Boolean) {
        emergencySwitch.isChecked = value
    }

    override fun showLoading() {
        progressView.visibility = View.VISIBLE
    }

    override fun hideOverlays() {
        progressView.visibility = View.GONE
    }

    override fun isLoadingPresented(): Boolean = progressView.visibility == View.VISIBLE
}