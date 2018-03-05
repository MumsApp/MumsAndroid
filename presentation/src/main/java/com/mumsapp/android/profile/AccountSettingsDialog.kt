package com.mumsapp.android.profile

import android.content.Context
import android.os.Bundle
import butterknife.ButterKnife
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseDialog
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.base.LifecycleView
import com.mumsapp.android.di.components.ActivityComponent
import javax.inject.Inject

class AccountSettingsDialog(context: Context) : BaseDialog(context), AccountSettingsView {

    @Inject
    lateinit var presenter: AccountSettingsPresenter

    override fun <T : LifecyclePresenter<LifecycleView>> getPresenter(): T = presenter as T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup(context)
    }

    private fun setup(context: Context) {
        getComponent().inject(this)
        setContentView(R.layout.dialog_account_settings)
        ButterKnife.bind(this)
    }
}