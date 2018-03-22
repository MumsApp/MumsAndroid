package com.mumsapp.android.product

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
import com.mumsapp.android.ui.views.GridRecyclerView
import com.mumsapp.android.ui.views.TopBar
import com.mumsapp.domain.model.product.ProductItem
import javax.inject.Inject

class MyProductsFragment : BaseFragment(), MyProductsView {

    @Inject
    lateinit var presenter: MyProductsPresenter

    @Inject
    lateinit var adapter: MyProductsItemsAdapter

    @BindView(R.id.my_products_top_bar)
    lateinit var topBar: TopBar

    @BindView(R.id.my_products_recycler_view)
    lateinit var recyclerView: GridRecyclerView

    override fun <T : LifecyclePresenter<LifecycleView>> getLifecyclePresenter() = presenter as T

    companion object {
        fun getInstance() = MyProductsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent(ActivityComponent::class.java)?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_my_products, container, false)
        setUnbinder(ButterKnife.bind(this, v))
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachViewWithLifecycle(this)
        topBar.setLeftButtonClickListener { presenter.onBackClick() }
    }

    @OnClick(R.id.my_products_upload_button)
    fun onUploadProductClick() {
        presenter.onUploadProductClick()
    }

    override fun showItems(items: List<ProductItem>, editClickListener: (item: ProductItem) -> Unit) {
        adapter.items = items
        adapter.notifyDataSetChanged()

        if(recyclerView.adapter == null) {
            adapter.edditButtonClickListener = editClickListener
            recyclerView.adapter = adapter
        }
    }
}