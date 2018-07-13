package com.mumsapp.android.profile

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import butterknife.BindView
import butterknife.ButterKnife
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseDialog
import com.mumsapp.android.base.BasePresenter
import com.mumsapp.android.base.BaseView
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.ui.views.TopBar
import com.mumsapp.domain.model.user.UserResponse.Child
import javax.inject.Inject

class AddChildDialog(context: Context) :
        BaseDialog(context), AddChildView {

    @Inject
    lateinit var presenter: AddChildPresenter

    @BindView(R.id.add_child_top_bar)
    lateinit var topBar: TopBar

    private var actionListener: ((child: Child) -> Unit)? = null

    override fun <T : BasePresenter<BaseView>> getPresenter(): T = presenter as T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    private fun setup() {
        getComponent(ActivityComponent::class.java)?.inject(this)
        setContentView(R.layout.dialog_add_child)
        configureWindow()
        ButterKnife.bind(this)
        topBar.setRightButtonClickListener {
            presenter.onCloseClick()
        }
        presenter.attachView(this)
    }

    fun show(selectedSex: Int, selectedChild: Child?,
             listener: (child: Child) -> Unit) {
        actionListener = listener
        super.show()
        presenter.setArguments(selectedSex, selectedChild)
    }

    override fun setTitle(title: String) {
    }

    override fun dismissView() {
        dismiss()
    }

    override fun setSex(image: Drawable, sexName: String) {
    }

    override fun setAge(age: String) {
    }

    override fun setAgeUnitCheck(weeksChecked: Boolean, monthsChecked: Boolean, yearsChecked: Boolean) {
    }
}