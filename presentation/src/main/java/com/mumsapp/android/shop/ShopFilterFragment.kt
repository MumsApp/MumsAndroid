package com.mumsapp.android.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseFragment
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.base.LifecycleView
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.ui.views.BaseButton
import com.mumsapp.android.ui.views.TopBar
import com.mumsapp.android.ui.widgets.DistanceRangeWidget
import com.mumsapp.android.ui.widgets.PriceRangeWidget
import javax.inject.Inject

class ShopFilterFragment : BaseFragment(), ShopFilterView {

    @Inject
    lateinit var presenter: ShopFilterPresenter

    @BindView(R.id.select_product_category_top_bar)
    lateinit var topBar: TopBar

    @BindView(R.id.shop_filter_select_category)
    lateinit var selectCategoryButton: BaseButton

    @BindView(R.id.shop_filter_price_range_widget)
    lateinit var priceRangeWidget: PriceRangeWidget

    @BindView(R.id.shop_filter_distance_range_widget)
    lateinit var distanceRangeWidget: DistanceRangeWidget

    override fun <T : LifecyclePresenter<LifecycleView>> getLifecyclePresenter() = presenter as T

    companion object {
        fun getInstance(): ShopFilterFragment {
            return ShopFilterFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent(ActivityComponent::class.java)?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_shop_filter, container, false)
        setUnbinder(ButterKnife.bind(this, v))
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachViewWithLifecycle(this)
        topBar.setLeftButtonClickListener { presenter.onBackClick() }
        priceRangeWidget.setSwitchChangeListener(presenter::onGiveItToFreeCheckedChanged)
    }

    @OnClick(R.id.shop_filter_select_category)
    fun onSelectCategoryClick() {
        presenter.onSelectCategoryClick()
    }

    override fun setCategoryName(categoryName: String?) {
        selectCategoryButton.text = categoryName
    }

    override fun enablePriceSelection() {
        priceRangeWidget.setSelectionEnabled(true)
    }

    override fun disablePriceSelection() {
        priceRangeWidget.setSelectionEnabled(false)
    }
}