package com.mumsapp.android.mums_app_offers

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
import com.mumsapp.android.common.dialogs.ConfirmationDialog
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.navigation.DialogsProvider
import com.mumsapp.android.product.MyWishlistItemsAdapter
import com.mumsapp.android.ui.views.GridRecyclerView
import com.mumsapp.android.ui.views.ImagesSlider
import com.mumsapp.android.ui.views.TopBar
import com.mumsapp.android.ui.widgets.MumsAppOfferDetailsWidget
import com.mumsapp.android.util.MUMS_APP_OFFER_ID_KEY
import com.mumsapp.android.util.PRODUCT_ID_KEY
import com.mumsapp.domain.model.product.ProductItem
import ss.com.bannerslider.banners.Banner
import javax.inject.Inject

class MumsAppOfferDetailsFragment : BaseFragment(), MumsAppOfferDetailsView {

    @Inject
    lateinit var presenter: MumsAppOfferDetailsPresenter

    @Inject
    lateinit var adapter: MyWishlistItemsAdapter

    @Inject
    lateinit var dialogsProvider: DialogsProvider

    @BindView(R.id.mums_app_offer_details_top_bar)
    lateinit var topBar: TopBar

    @BindView(R.id.mums_app_offer_details_image_slider)
    lateinit var imagesSlider: ImagesSlider

    @BindView(R.id.mums_app_offer_details_widget)
    lateinit var detailsWidget: MumsAppOfferDetailsWidget

    @BindView(R.id.mums_app_offer_details_recycler_view)
    lateinit var recyclerView: GridRecyclerView

    private var confirmationDialog: ConfirmationDialog? = null

    override fun <T : LifecyclePresenter<LifecycleView>> getLifecyclePresenter() = presenter as T

    companion object {
        fun getInstance(offerId: Int): MumsAppOfferDetailsFragment {
            val fragment = MumsAppOfferDetailsFragment()
            fragment.arguments = createArgBundle(offerId)

            return fragment
        }

        private fun createArgBundle(productId: Int): Bundle {
            val args = Bundle()
            args.putInt(MUMS_APP_OFFER_ID_KEY, productId)

            return args
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent(ActivityComponent::class.java)?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_mums_app_offer_details, container, false)
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
        presenter.setArguments(productId)
    }

    override fun showImages(images: List<Banner>) {
        imagesSlider.setBanners(images)
    }

    override fun showDetails(title: String, description: String) {
        detailsWidget.setTitle(title)
        detailsWidget.setDescription(description)
        detailsWidget.setGetVoucherButtonClickListener(presenter::onGetVoucherClick)
        detailsWidget.setFollowButtonClickListener(presenter::onFollowClick)
    }

    override fun showOtherItems(items: List<ProductItem>) {
        adapter.items = items
        adapter.notifyDataSetChanged()

        if(recyclerView.adapter == null) {
            recyclerView.adapter = adapter
        }
    }

    override fun showGetVoucherDialog(title: String, description: String, confirmButtonText: String) {
        if(confirmationDialog == null) {
            confirmationDialog = dialogsProvider.createConfirmationDialog()
        }

        confirmationDialog!!.show(title, description, confirmButtonText, presenter::onGetVoucherConfirmationClick)
    }
}