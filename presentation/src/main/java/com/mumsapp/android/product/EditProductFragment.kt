package com.mumsapp.android.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.mumsapp.android.R
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.base.LifecycleView
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.shop.ReadableShopProduct
import com.mumsapp.domain.utils.READABLE_SHOP_PRODUCT_KEY
import javax.inject.Inject

class EditProductFragment : BaseProductFormFragment(), EditProductView {

    @Inject
    lateinit var presenter: EditProductPresenter

    override fun <T : LifecyclePresenter<LifecycleView>> getLifecyclePresenter() = presenter as T

    companion object {
        fun getInstance(product: ReadableShopProduct) : EditProductFragment {
            val fragment = EditProductFragment()
            fragment.arguments = EditProductFragment.createArgBundle(product)

            return fragment
        }

        private fun createArgBundle(product: ReadableShopProduct): Bundle {
            val args = Bundle()
            args.putSerializable(READABLE_SHOP_PRODUCT_KEY, product)

            return args
        }
    }

    private fun passArgumentsToPresenter() {
        val product = arguments?.getSerializable(READABLE_SHOP_PRODUCT_KEY) as ReadableShopProduct
        presenter.setArguments(product)
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
        passArgumentsToPresenter()
        presenter.attachViewWithLifecycle(this)
        topBar.setLeftButtonClickListener { presenter.onBackClick() }
    }

    override fun getFormPresenter(): BaseProductFormPresenter<BaseProductFormView> {
        return presenter as BaseProductFormPresenter<BaseProductFormView>
    }

    override fun showInitialDetails(title: String?, category: String?, price: String?, description: String?) {
        editProductDetailsWidget.setTitle(title)
        editProductDetailsWidget.setCategory(category)
        editProductDetailsWidget.setPrice(price)
        editProductDetailsWidget.setDescription(description)
    }
}