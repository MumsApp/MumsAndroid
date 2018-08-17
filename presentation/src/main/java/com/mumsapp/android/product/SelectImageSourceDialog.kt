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
import com.mumsapp.android.ui.views.TopBar
import javax.inject.Inject

class SelectImageSourceDialog(context: Context) : BaseDialog(context), SelectImageSourceView {

    @Inject
    lateinit var presenter: SelectImageSourcePresenter

    @BindView(R.id.select_image_source_top_bar)
    lateinit var topBar: TopBar

    private var galleryClickListener: (() -> Unit)? = null
    private var cameraClickListener: (() -> Unit)? = null

    override fun <T : BasePresenter<BaseView>> getPresenter() = presenter as T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    private fun setup() {
        getComponent(ActivityComponent::class.java)?.inject(this)
        setContentView(R.layout.dialog_select_image_source)
        configureWindow()
        ButterKnife.bind(this)
        topBar.setRightButtonClickListener {
            presenter.onCloseClick()
        }
        presenter.attachView(this)
    }

    fun show(galleryClickListener: () -> Unit, cameraClickListener: () -> Unit) {
        super.show()

        this.galleryClickListener = galleryClickListener
        this.cameraClickListener = cameraClickListener
    }

    @OnClick(R.id.select_image_source_gallery)
    fun onGalleryClick() {
        presenter.onGalleryClick()
    }

    @OnClick(R.id.select_image_source_camera)
    fun onCameraClick() {
        presenter.onCameraClick()
    }

    override fun dismissView() {
        this.galleryClickListener = null
        this.cameraClickListener = null
        dismiss()
    }

    override fun deliverGalleryResults() {
        galleryClickListener?.invoke()
    }

    override fun deliverCameraResults() {
        cameraClickListener?.invoke()
    }
}