package com.mumsapp.android.profile

import android.content.Context
import android.os.Bundle
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mumsapp.android.R
import com.mumsapp.android.base.*
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.ui.views.BaseInput
import com.mumsapp.android.ui.views.TopBar
import javax.inject.Inject

class UserDetailsSettingsDialog(context: Context) : BaseDialog(context), UserDetailsSettingsView {

    @Inject
    lateinit var presenter: UserDetailsSettingsPresenter

    @BindView(R.id.user_details_settings_top_bar)
    lateinit var topBar: TopBar

    @BindView(R.id.user_details_settings_first_name)
    lateinit var firstNameInput: BaseInput

    @BindView(R.id.user_details_settings_last_name)
    lateinit var lastNameInput: BaseInput

    @BindView(R.id.user_details_settings_description)
    lateinit var descriptionInput: BaseInput

    private var actionListener: ((firstName: String, lastName: String, description: String) -> Unit)? = null

    override fun <T : BasePresenter<BaseView>> getPresenter() = presenter as T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    private fun setup() {
        getComponent(ActivityComponent::class.java)?.inject(this)
        setContentView(R.layout.dialog_user_details_settings)
        configureWindow()
        ButterKnife.bind(this)
        topBar.setRightButtonClickListener {
            presenter.onCloseClick()
        }
        presenter.attachView(this)
    }

    fun show(firstName: String?, lastName: String?, description: String?,
             listener: (firstName: String, lastName: String, description: String) -> Unit) {
        actionListener = listener
        super.show()
        presenter.setArguments(firstName, lastName, description)
    }

    @OnClick(R.id.user_details_settings_save)
    fun onSaveClick() {
        val firstName = firstNameInput.text
        val lastName = lastNameInput.text
        val description = descriptionInput.text

        presenter.onSaveClick(firstName, lastName, description)
    }

    override fun dismissView() {
        actionListener = null
        dismiss()
    }

    override fun showInitialData(firstName: String?, lastName: String?, description: String?) {
        firstNameInput.text = firstName
        lastNameInput.text = lastName
        descriptionInput.text = description
    }

    override fun showFirstNameError(error: String) {
        firstNameInput.error = error
    }

    override fun showLastNameError(error: String) {
        lastNameInput.error = error
    }

    override fun deliverResults(firstName: String, lastName: String, description: String) {
        actionListener?.invoke(firstName, lastName, description)
        actionListener = null
    }
}