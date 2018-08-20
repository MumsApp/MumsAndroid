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
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.ui.views.ImagesSlider
import com.mumsapp.android.ui.views.TopBar
import com.mumsapp.android.ui.widgets.LocationWidget
import com.mumsapp.android.ui.widgets.ProductDetailsWidget
import com.mumsapp.domain.utils.PRODUCT_ID_KEY
import ss.com.bannerslider.banners.Banner
import javax.inject.Inject

class ProductFragment : BaseFragment(), ProductView {

    @Inject
    lateinit var presenter: ProductPresenter

    @BindView(R.id.product_top_bar)
    lateinit var topBar: TopBar

    @BindView(R.id.product_image_slider)
    lateinit var imagesSlider: ImagesSlider

    @BindView(R.id.product_product_details_widget)
    lateinit var productDetailsWidget: ProductDetailsWidget

    @BindView(R.id.product_location_widget)
    lateinit var locationWidget: LocationWidget

    override fun <T : LifecyclePresenter<LifecycleView>> getLifecyclePresenter() = presenter as T

    companion object {
        fun getInstance(productId: Int): ProductFragment {
            val fragment = ProductFragment()
            fragment.arguments = createArgBundle(productId)

            return fragment
        }

        private fun createArgBundle(productId: Int): Bundle {
            val args = Bundle()
            args.putInt(PRODUCT_ID_KEY, productId)

            return args
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent(ActivityComponent::class.java)?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_product, container, false)
        setUnbinder(ButterKnife.bind(this, v))
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        passArgumentsToPresenter()
        presenter.attachViewWithLifecycle(this)
        topBar.setLeftButtonClickListener { presenter.onBackClick() }
    }

    private fun passArgumentsToPresenter() {
        val productId = arguments?.getInt(PRODUCT_ID_KEY)
        presenter.setArguments(productId!!)
    }

    override fun setProductDetails(images: List<Banner>, productName: String, category: String,
                                   avatarUrl: String?, userName: String, price: String,
                                   distance: String?, description: String) {
        imagesSlider.setBanners(images)
        productDetailsWidget.setProductName(productName)
        productDetailsWidget.setCategory(category)
        productDetailsWidget.setAvatar(avatarUrl)
        productDetailsWidget.setUserName(userName)
        productDetailsWidget.setPrice(price)
        productDetailsWidget.setDistance(distance)
        productDetailsWidget.setDescription(description)
        productDetailsWidget.setContactButtonListener { presenter.onContactClick() }
        productDetailsWidget.setUserClickListener { presenter.onUserClick() }
    }

    override fun setLocationDetails(locationName: String?, latitude: Double, longitude: Double) {
        locationWidget.setLocationName(locationName)
        locationWidget.setMapCoordinates(latitude, longitude)
    }
}