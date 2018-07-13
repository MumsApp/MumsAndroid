package com.mumsapp.android.profile

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.CheckBox
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnCheckedChanged
import butterknife.OnClick
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseDialog
import com.mumsapp.android.base.BasePresenter
import com.mumsapp.android.base.BaseView
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.ui.views.*
import com.mumsapp.domain.model.user.UserResponse.Child
import javax.inject.Inject

class AddChildDialog(context: Context) :
        BaseDialog(context), AddChildView {

    @Inject
    lateinit var presenter: AddChildPresenter

    @BindView(R.id.add_child_top_bar)
    lateinit var topBar: TopBar

    @BindView(R.id.add_child_image)
    lateinit var imageView: BaseImageView

    @BindView(R.id.add_child_sex)
    lateinit var sexView: BaseTextView

    @BindView(R.id.add_child_age_incrementer)
    lateinit var ageIncrementerView: NumberIncrementer

    @BindView(R.id.add_child_checkbox_week)
    lateinit var weekCheckbox: BaseCheckbox

    @BindView(R.id.add_child_checkbox_month)
    lateinit var monthCheckbox: BaseCheckbox

    @BindView(R.id.add_child_checkbox_year)
    lateinit var yearheckbox: BaseCheckbox


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

    @OnCheckedChanged(R.id.add_child_checkbox_week, R.id.add_child_checkbox_month, R.id.add_child_checkbox_year)
    fun onCheckedChanged(view: CheckBox, value: Boolean) {
        if(value) {
            weekCheckbox.isChecked = false
            monthCheckbox.isChecked = false
            yearheckbox.isChecked = false

            view.isChecked = true
        }
    }

    @OnClick(R.id.add_child_save)
    fun onSaveClick() {
        presenter.onSaveClick(ageIncrementerView.getValue(), weekCheckbox.isChecked,
                monthCheckbox.isChecked, yearheckbox.isChecked)
    }

    override fun setTitle(title: String) {
        topBar.setTitleText(title)
    }

    override fun dismissView() {
        dismiss()
    }

    override fun setSex(image: Drawable, sexName: String) {
        imageView.setImageDrawable(image)
        sexView.text = sexName
    }

    override fun setAge(age: Int) {
        ageIncrementerView.setValue(age)
    }

    override fun setAgeUnitCheck(weeksChecked: Boolean, monthsChecked: Boolean, yearsChecked: Boolean) {
        weekCheckbox.isChecked = weeksChecked
        monthCheckbox.isChecked = monthsChecked
        yearheckbox.isChecked = yearsChecked
    }

    override fun deliverAction(child: Child) {
        actionListener?.invoke(child)
    }
}