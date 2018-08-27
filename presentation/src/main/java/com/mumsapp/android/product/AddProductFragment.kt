package com.mumsapp.android.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseFragment
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.base.LifecycleView
import com.mumsapp.android.common.dialogs.ConfirmationWithAvatarDialog
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.navigation.ActivitiesNavigationService
import com.mumsapp.android.navigation.DialogsProvider
import com.mumsapp.android.ui.views.BaseImageView
import com.mumsapp.android.ui.views.BaseTextView
import com.mumsapp.android.ui.views.HorizontalRecyclerView
import com.mumsapp.android.ui.views.TopBar
import com.mumsapp.android.ui.widgets.EditProductDetailsWidget
import com.mumsapp.android.ui.widgets.LocationWidget
import com.mumsapp.android.util.ImagesLoader
import javax.inject.Inject

class AddProductFragment : BaseProductFormFragment(), AddProductView {

    @Inject
    lateinit var presenter: AddProductPresenter

    companion object {
        fun getInstance() = AddProductFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent(ActivityComponent::class.java)?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_add_product, container, false)
        setUnbinder(ButterKnife.bind(this, v))
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachViewWithLifecycle(this)
        topBar.setLeftButtonClickListener { presenter.onBackClick() }
        editProductDetailsWidget.setOnAddCategoryClickListener { presenter.onAddCategoryClick() }
        editProductDetailsWidget.setOnAskForCategoryClickListener { presenter.onAskForNewCategoryClick() }
        locationWidget.setWidgetButtonListener { presenter.onEditLocationClick() }
    }

    override fun getFormPresenter(): BaseProductFormPresenter<BaseProductFormView> {
        return presenter as BaseProductFormPresenter<BaseProductFormView>
    }
}