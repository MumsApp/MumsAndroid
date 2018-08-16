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
import com.mumsapp.android.navigation.DialogsProvider
import com.mumsapp.android.ui.views.BaseTextView
import com.mumsapp.android.ui.views.CardsRecyclerView
import com.mumsapp.android.ui.views.TopBar
import com.mumsapp.domain.model.product.ProductItem
import javax.inject.Inject

class ShopFragment : BaseFragment(), ShopView {

    @Inject
    lateinit var presenter: ShopPresenter

    @Inject
    lateinit var dialogsProvider: DialogsProvider

    @Inject
    lateinit var adapter: ShopItemsAdapter

    @BindView(R.id.shop_top_bar)
    lateinit var topBar: TopBar

    @BindView(R.id.shop_filter_category)
    lateinit var categoryView: BaseTextView

    @BindView(R.id.shop_filter_price)
    lateinit var priceView: BaseTextView

    @BindView(R.id.shop_filter_distance)
    lateinit var distanceView: BaseTextView

    @BindView(R.id.shop_recycler_view)
    lateinit var recyclerView: CardsRecyclerView

    private var shopMenuDialog: ShopMenuDialog? = null

    override fun <T : LifecyclePresenter<LifecycleView>> getLifecyclePresenter() = presenter as T

    companion object {
        fun getInstance(): ShopFragment {
            return ShopFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent(ActivityComponent::class.java)?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_shop, container, false)
        setUnbinder(ButterKnife.bind(this, v))
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachViewWithLifecycle(this)
        topBar.setLeftButtonClickListener { presenter.onAddClick() }
        topBar.setRightButtonClickListener { presenter.onMenuButtonClick() }
        topBar.setSearchListener(presenter::onSearch)
    }

    @OnClick(R.id.shop_filter_button)
    fun onFilterButtonClick() {
        presenter.onFilterClick()
    }

    override fun openMenuDialog() {
        if (shopMenuDialog == null) {
            shopMenuDialog = dialogsProvider.createShopMenuDialog()
        }

        shopMenuDialog?.setOnSearchClickListener(presenter::onDialogSearchButtonClick)
        shopMenuDialog?.show()
    }

    override fun closeMenuDialog() {
        shopMenuDialog?.dismissView()
    }

    override fun startSearching() {
        topBar.requestSearchFocus()
    }

    override fun showFilterValues(category: String, price: String, distance: String) {
        categoryView.text = category
        priceView.text = price
        distanceView.text = distance
    }

    override fun showItems(items: List<ProductItem>, checkboxChangeListener: (item: ProductItem, value: Boolean) -> Unit) {
        adapter.items = items
        adapter.notifyDataSetChanged()

        if(recyclerView.adapter == null) {
            adapter.checkboxChangeListener = checkboxChangeListener
            adapter.setItemsClickListener(presenter::onProductClick)
            recyclerView.adapter = adapter
        }
    }
}