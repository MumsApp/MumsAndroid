package com.mumsapp.android.common.dialogs

import android.content.Context
import android.os.Bundle
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseDialog
import com.mumsapp.android.base.BasePresenter
import com.mumsapp.android.base.BaseView
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.ui.views.BaseButton
import com.mumsapp.android.ui.views.BaseTextView
import com.mumsapp.android.ui.views.TopBar
import javax.inject.Inject

class ConfirmationDialog(context: Context) : BaseDialog(context), ConfirmationView {

    @Inject
    lateinit var presenter: ConfirmationPresenter

    @BindView(R.id.confirmation_top_bar)
    lateinit var topBar: TopBar

    @BindView(R.id.confirmation_description)
    lateinit var descriptionView: BaseTextView

    @BindView(R.id.confirmation_button_confirm)
    lateinit var confirmButton: BaseButton

    private var confirmButtonListener: (() -> Unit)? = null

    override fun <T : BasePresenter<BaseView>> getPresenter() = presenter as T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    private fun setup() {
        getComponent(ActivityComponent::class.java)?.inject(this)
        setContentView(R.layout.dialog_confirmation)
        configureWindow()
        ButterKnife.bind(this)
        topBar.setRightButtonClickListener {
            presenter.onCloseClick()
        }
        presenter.attachView(this)
    }

    fun show(title: String, description: String, confirmButtonText: String, confirmButtonListener: () -> Unit) {
        super.show()

        this.confirmButtonListener = confirmButtonListener

        presenter.setArguments(title, description, confirmButtonText)
        presenter.start()
    }

    @OnClick(R.id.confirmation_button_confirm)
    fun onConfirmButtonClick() {
        presenter.onConfirmButtonClick()
    }

    override fun showContent(title: String, description: String, confirmButtonText: String) {
        topBar.setTitleText(title)
        descriptionView.text = description
        confirmButton.text = confirmButtonText
    }

    override fun dismissView() {
        confirmButtonListener = null
        dismiss()
    }

    override fun deliverConfirmButtonClick() {
        confirmButtonListener?.invoke()
    }
}