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
import com.mumsapp.android.navigation.DialogsProvider
import com.mumsapp.android.ui.views.GridRecyclerView
import com.mumsapp.android.ui.views.TopBar
import com.mumsapp.domain.model.product.Product
import javax.inject.Inject

class MyWishlistFragment : BaseFragment(), MyWishlistView {

    @Inject
    lateinit var presenter: MyWishlistPresenter

    @Inject
    lateinit var adapter: MyWishlistItemsAdapter

    @Inject
    lateinit var dialogsProvider: DialogsProvider

    @BindView(R.id.my_wishlist_top_bar)
    lateinit var topBar: TopBar

    @BindView(R.id.my_wishlist_recycler_view)
    lateinit var recyclerView: GridRecyclerView

    private var removeProductDialog: RemoveProductDialog? = null

    override fun <T : LifecyclePresenter<LifecycleView>> getLifecyclePresenter() = presenter as T

    companion object {
        fun getInstance() = MyWishlistFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent(ActivityComponent::class.java)?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_my_wishlist, container, false)
        setUnbinder(ButterKnife.bind(this, v))
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachViewWithLifecycle(this)
        topBar.setLeftButtonClickListener { presenter.onBackClick() }
    }

    override fun showItems(items: List<Product>, checkboxChangeListener: (item: Product, value: Boolean) -> Unit) {
        adapter.items = items
        adapter.notifyDataSetChanged()

        if(recyclerView.adapter == null) {
            adapter.checkboxChangeListener = checkboxChangeListener
            recyclerView.adapter = adapter
        }
    }

    override fun openRemoveProductDialog(item: Product, bottomText: String, listener: () -> Unit) {
        if(removeProductDialog == null) {
            removeProductDialog = dialogsProvider.createRemoveProductDialog()
        }

        removeProductDialog?.show(item, bottomText, listener)
    }

}