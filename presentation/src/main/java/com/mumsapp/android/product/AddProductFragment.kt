package com.mumsapp.android.product

import android.app.Activity
import android.content.Intent
import android.net.Uri
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
import com.mumsapp.android.ui.views.BaseImageView
import com.mumsapp.android.ui.views.TopBar
import com.mumsapp.android.util.CAMERA_REQUEST_CODE
import com.mumsapp.android.util.GALLERY_REQUEST_CODE
import com.mumsapp.android.util.ImagesLoader
import javax.inject.Inject

class AddProductFragment : BaseFragment(), AddProductView {

    @Inject
    lateinit var presenter: AddProductPresenter

    @Inject
    lateinit var dialogsProvider: DialogsProvider

    @Inject
    lateinit var imagesLoader: ImagesLoader

    @BindView(R.id.add_product_top_bar)
    lateinit var topBar: TopBar

    @BindView(R.id.add_product_image_header)
    lateinit var headerImageView: BaseImageView

    private var selectImageSourceDialog: SelectImageSourceDialog? = null

    override fun <T : LifecyclePresenter<LifecycleView>> getLifecyclePresenter() = presenter as T

    companion object {
        fun getInstance() = AddProductFragment()
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
        presenter.attachViewWithLifecycle(this)
        topBar.setLeftButtonClickListener { presenter.onBackClick() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    presenter.onGalleryImageReceived(data!!.data)
                }

                CAMERA_REQUEST_CODE -> {
                    presenter.onCameraImageReceived()
                }
            }
        }
    }

    @OnClick(R.id.add_product_add_image_button, R.id.add_product_add_image_text,
            R.id.add_product_add_image_background)
    fun onAddPhotoClick() {
        presenter.onAddPhotoClick()
    }

    override fun showSelectImageSourceDialog() {
        if (selectImageSourceDialog == null) {
            selectImageSourceDialog = dialogsProvider.createSelectImageSourceDialog()
        }

        selectImageSourceDialog?.show(presenter::onGalleryClick, presenter::onCameraClick)
    }

    override fun showImageHeader(uri: Uri) {
        imagesLoader.load(uri, headerImageView)
    }
}