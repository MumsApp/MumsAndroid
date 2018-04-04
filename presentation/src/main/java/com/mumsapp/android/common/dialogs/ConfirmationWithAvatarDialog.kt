package com.mumsapp.android.common.dialogs

import android.content.Context
import android.net.Uri
import android.os.Bundle
import butterknife.BindView
import butterknife.ButterKnife
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseDialog
import com.mumsapp.android.base.BasePresenter
import com.mumsapp.android.base.BaseView
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.ui.views.TopBar
import javax.inject.Inject

class ConfirmationWithAvatarDialog(context: Context) : BaseDialog(context), ConfirmationWithAvatarView {

    @Inject
    lateinit var presenter: ConfirmationWithAvatarPresenter

    @BindView(R.id.confirmation_with_avatar_top_bar)
    lateinit var topBar: TopBar

    private var confirmationListener: (() -> Unit)? = null
    private var cancelListener: (() -> Unit)? = null

    override fun <T : BasePresenter<BaseView>> getPresenter() = presenter as T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    private fun setup() {
        getComponent(ActivityComponent::class.java)?.inject(this)
        setContentView(R.layout.dialog_confirmation_with_avatar)
        configureWindow()
        ButterKnife.bind(this)
        topBar.setRightButtonClickListener {
            presenter.onCloseClick()
        }
        presenter.attachView(this)
    }

    fun show(avatarUri: Uri?, avatarTitle: String?, title: String, description: String,
             confirmButtonText: String, cancelButtonText: String, confirmationListener: () -> Unit,
             cancelListener: () -> Unit) {
        super.show()

        this.confirmationListener = confirmationListener
        this.cancelListener = cancelListener

        presenter.setArguments(avatarUri, avatarTitle, title, description, confirmButtonText, cancelButtonText)
        presenter.start()
    }

    override fun showAvatar(avatarUri: Uri, avatarTitle: String?) {
    }

    override fun setContent(title: String, description: String, confirmButtonText: String, cancelButtonText: String) {
    }

    override fun dismissView() {
        confirmationListener = null
        cancelListener = null
        dismiss()
    }

    override fun deliverConfirmButtonClick() {
        confirmationListener?.invoke()
    }

    override fun deliverCancelButtonClick() {
        cancelListener?.invoke()
    }
}