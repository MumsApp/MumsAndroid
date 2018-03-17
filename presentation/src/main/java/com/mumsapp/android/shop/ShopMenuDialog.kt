package com.mumsapp.android.shop

import android.content.Context
import android.os.Bundle
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseDialog
import com.mumsapp.android.base.BasePresenter
import com.mumsapp.android.base.BaseView
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.ui.views.BaseImageView
import com.mumsapp.android.ui.views.BaseTextView
import com.mumsapp.android.ui.views.TopBar
import javax.inject.Inject

class ShopMenuDialog(context: Context) : BaseDialog(context), ShopMenuView {

    @Inject
    lateinit var presenter: ShopMenuPresenter

    @BindView(R.id.shop_menu_top_bar)
    lateinit var topBar: TopBar

    @BindView(R.id.shop_menu_search_icon)
    lateinit var searchIcon: BaseImageView

    @BindView(R.id.shop_menu_search)
    lateinit var searchButton: BaseTextView

    private var searchListener: (() -> Unit)? = null

    override fun <T : BasePresenter<BaseView>> getPresenter() = presenter as T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    private fun setup() {
        getComponent(ActivityComponent::class.java)?.inject(this)
        setContentView(R.layout.dialog_shop_menu)
        configureWindow()
        ButterKnife.bind(this)
        topBar.setRightButtonClickListener {
            presenter.onCloseClick()
        }
        presenter.attachView(this)
    }

    override fun dismissView() {
        searchIcon.setOnClickListener(null)
        searchButton.setOnClickListener(null)
        dismiss()
    }

    fun setOnSearchClickListener(listener: () -> Unit) {
        searchListener = listener
    }

    @OnClick(R.id.shop_menu_search, R.id.shop_menu_search_icon)
    fun onSearchClick() {
        presenter.onSearchClick()
    }

    @OnClick(R.id.shop_menu_my_products, R.id.shop_menu_my_products_icon)
    fun onMyProductsClick() {
        presenter.onMyProductsClick()
    }

    @OnClick(R.id.shop_menu_my_wishlist, R.id.shop_menu_my_wishlist_icon)
    fun onMywishlistClick() {
        presenter.onMywishlistClick()
    }

    override fun deliverSearchResults() {
        searchListener?.invoke()
    }
}