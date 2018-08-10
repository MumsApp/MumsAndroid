package com.mumsapp.android.shop

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.brandongogetap.stickyheaders.StickyLayoutManager
import com.brandongogetap.stickyheaders.exposed.StickyHeaderListener
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseFragment
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.base.LifecycleView
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.ui.views.BaseRecyclerView
import com.mumsapp.android.ui.views.TopBar
import javax.inject.Inject

class SelectProductCategoryFragment : BaseFragment(), SelectProductCategoryView {

    @Inject
    lateinit var presenter: SelectProductCategoryPresenter

    @Inject
    lateinit var adapter: SelectProductCategoryAdapter

    @BindView(R.id.select_product_category_top_bar)
    lateinit var topBar: TopBar

    @BindView(R.id.select_product_category_recycler)
    lateinit var recyclerView: BaseRecyclerView

    override fun <T : LifecyclePresenter<LifecycleView>> getLifecyclePresenter() = presenter as T

    companion object {
        fun getInstance(): SelectProductCategoryFragment {
            return SelectProductCategoryFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent(ActivityComponent::class.java)?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_select_product_category, container, false)
        setUnbinder(ButterKnife.bind(this, v))
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachViewWithLifecycle(this)
        topBar.setLeftButtonClickListener { presenter.onBackClick() }
        recyclerView.layoutManager = StickyLayoutManager(activity, adapter)
    }

    override fun showCategories(list: List<SelectProductCategoryItem>,
                                itemsClickListener: (item: SelectProductCategoryItem) -> Unit) {
        adapter.items = list
        adapter.notifyDataSetChanged()

        if(recyclerView.adapter == null) {
            recyclerView.adapter = adapter
            adapter.setItemsClickListener(itemsClickListener)
        }
    }
}