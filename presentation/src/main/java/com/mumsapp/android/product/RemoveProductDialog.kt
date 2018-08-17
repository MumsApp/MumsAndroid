package com.mumsapp.android.product

import android.content.Context
import android.os.Bundle
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseDialog
import com.mumsapp.android.base.BasePresenter
import com.mumsapp.android.base.BaseView
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.ui.views.BaseTextView
import com.mumsapp.android.ui.views.CircleImageView
import com.mumsapp.android.ui.views.TopBar
import com.mumsapp.domain.model.product.Product
import javax.inject.Inject

class RemoveProductDialog(context: Context) : BaseDialog(context), RemoveProductView {

    @Inject
    lateinit var presenter: RemoveProductPresenter

    @BindView(R.id.remove_product_top_bar)
    lateinit var topBar: TopBar

    @BindView(R.id.remove_product_image)
    lateinit var imageView: CircleImageView

    @BindView(R.id.remove_product_name)
    lateinit var nameView: BaseTextView

    @BindView(R.id.remove_product_bottom_text)
    lateinit var bottomTextView: BaseTextView

    private var confirmationListener: (() -> Unit)? = null

    override fun <T : BasePresenter<BaseView>> getPresenter() = presenter as T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    private fun setup() {
        getComponent(ActivityComponent::class.java)?.inject(this)
        setContentView(R.layout.dialog_remove_product)
        configureWindow()
        ButterKnife.bind(this)
        topBar.setRightButtonClickListener {
            presenter.onCloseClick()
        }
        presenter.attachView(this)
    }

    fun show(productItem: Product, bottomText: String, confirmationListener: () -> Unit) {
        super.show()

        this.confirmationListener = confirmationListener
        presenter.setArguments(productItem, bottomText)
        presenter.start()
    }

    @OnClick(R.id.remove_product_yes_button)
    fun onYesbuttonListener() {
        presenter.onYesButtonClick()
    }

    override fun dismissView() {
        confirmationListener = null
        dismiss()
    }

    override fun showProductInformation(imageUrl: String, productName: String, bottomText: String) {
        nameView.text = productName
        bottomTextView.text = bottomText
    }

    override fun deliverResults() {
        confirmationListener?.invoke()
    }
}