package com.mumsapp.android.common.dialogs

import android.content.Context
import android.net.Uri
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
import com.mumsapp.android.ui.views.CircleImageView
import com.mumsapp.android.ui.views.TopBar
import com.mumsapp.android.util.ImagesLoader
import javax.inject.Inject

class ConfirmationWithAvatarDialog(context: Context) : BaseDialog(context), ConfirmationWithAvatarView {

    @Inject
    lateinit var presenter: ConfirmationWithAvatarPresenter

    @Inject
    lateinit var imagesLoader: ImagesLoader

    @BindView(R.id.confirmation_with_avatar_top_bar)
    lateinit var topBar: TopBar

    @BindView(R.id.confirmation_with_avatar_image)
    lateinit var avatarView: CircleImageView

    @BindView(R.id.confirmation_with_avatar_image_title)
    lateinit var avatarTitleView: BaseTextView

    @BindView(R.id.confirmation_with_avatar_title)
    lateinit var titleView: BaseTextView

    @BindView(R.id.confirmation_with_avatar_description)
    lateinit var descriptionView: BaseTextView

    @BindView(R.id.confirmation_with_avatar_confirm_button)
    lateinit var confirmationButton: BaseButton

    @BindView(R.id.confirmation_with_avatar_cancel_button)
    lateinit var cancelButton: BaseTextView

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

    fun show(avatarUri: Uri?, avatarTitle: String, title: String, description: String?,
             confirmButtonText: String, cancelButtonText: String, confirmationListener: () -> Unit,
             cancelListener: () -> Unit) {
        super.show()

        this.confirmationListener = confirmationListener
        this.cancelListener = cancelListener

        presenter.setArguments(avatarUri, avatarTitle, title, description, confirmButtonText, cancelButtonText)
        presenter.start()
    }

    @OnClick(R.id.confirmation_with_avatar_confirm_button)
    fun onConfirmButtonClick() {
        presenter.onConfirmButtonClick()
    }

    @OnClick(R.id.confirmation_with_avatar_cancel_button)
    fun onCancelButtonClick() {
        presenter.onCancelButtonClick()
    }

    override fun showAvatar(avatarUri: Uri) {
        imagesLoader.load(avatarUri, avatarView)
    }

    override fun setContent(avatarTitle: String, title: String, description: String?, confirmButtonText: String, cancelButtonText: String) {
        avatarTitleView.text = avatarTitle
        titleView.text = title
        descriptionView.text = description
        confirmationButton.text = confirmButtonText
        cancelButton.text = cancelButtonText
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