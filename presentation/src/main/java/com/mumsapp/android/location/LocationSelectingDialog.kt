package com.mumsapp.android.location

import android.content.Context
import android.os.Bundle
import butterknife.ButterKnife
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseDialog
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.base.LifecycleView
import com.mumsapp.android.di.components.ActivityComponent
import javax.inject.Inject

class LocationSelectingDialog(context: Context) : BaseDialog(context), LocationSelectingView {

    @Inject
    lateinit var presenter: LocationSelectingPresenter

    override fun <T : LifecyclePresenter<LifecycleView>> getPresenter(): T = presenter as T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    private fun setup() {
        getComponent(ActivityComponent::class.java)?.inject(this)
        setContentView(R.layout.dialog_location_selecting)
        setUnbinder(ButterKnife.bind(this))
        presenter.attachView(this)
    }
}