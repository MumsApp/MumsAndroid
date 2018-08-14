package com.mumsapp.android.shop

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseFragment
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.base.LifecycleView
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.navigation.ActivitiesNavigationService
import com.mumsapp.android.ui.views.BaseButton
import com.mumsapp.android.ui.views.TopBar
import com.mumsapp.android.ui.widgets.DistanceRangeWidget
import com.mumsapp.android.ui.widgets.PriceRangeWidget
import com.mumsapp.android.util.GOOGLE_PLACES_REQUEST_CODE
import javax.inject.Inject

class ShopFilterFragment : BaseFragment(), ShopFilterView {

    @Inject
    lateinit var presenter: ShopFilterPresenter

    @Inject
    lateinit var activitiesNavigationService: ActivitiesNavigationService

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
        distanceRangeWidget.setOnSetLocationClickListener(presenter::onSetLocationClick)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GOOGLE_PLACES_REQUEST_CODE -> {
                    val place = PlaceAutocomplete.getPlace(context, data)
                    presenter.onLocationSelected(place)
                }
            }
        } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
            if (resultCode == GOOGLE_PLACES_REQUEST_CODE) {
                val status = PlaceAutocomplete.getStatus(context, data)
                presenter.onLocationError(status)
            }
        }
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

    override fun showEditLocationScreen() {
        activitiesNavigationService.openGooglePlacesOverlayActivity(this, GOOGLE_PLACES_REQUEST_CODE)
    }

    override fun showLocationName(name: String) {
        distanceRangeWidget.setSecondLineText(name)
    }

    override fun setGiveItForFree(value: Boolean) {
        priceRangeWidget.setSwitchValue(value)
    }

    override fun getGiveItForFree(): Boolean {
        return priceRangeWidget.getSwitchValue()
    }

    override fun setPrice(min: Int, max: Int) {
        priceRangeWidget.setSelectedMin(min)
        priceRangeWidget.setSelectedMax(max)
    }

    override fun getMinPrice(): Int {
        return priceRangeWidget.getSelectedMin()
    }

    override fun getMaxPrice(): Int {
        return priceRangeWidget.getSelectedMax()
    }

    override fun setDistance(min: Int, max: Int) {
        distanceRangeWidget.setSelectedMin(min)
        distanceRangeWidget.setSelectedMax(max)
    }

    override fun getMinDistance(): Int {
        return distanceRangeWidget.getSelectedMin()
    }

    override fun getMaxDistance(): Int {
        return distanceRangeWidget.getSelectedMax()
    }
}