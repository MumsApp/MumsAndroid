package com.mumsapp.android.lobby

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
import com.mumsapp.android.product.SelectImageSourceDialog
import com.mumsapp.android.ui.views.AddPhotoButton
import com.mumsapp.android.ui.views.BaseImageView
import com.mumsapp.android.ui.views.TopBar
import com.mumsapp.android.util.*
import javax.inject.Inject

class CreateLobbyTopicFragment : BaseFragment(), CreateLobbyTopicView {

    @Inject
    lateinit var presenter: CreateLobbyTopicPresenter

    @Inject
    lateinit var dialogsProvider: DialogsProvider

    @Inject
    lateinit var imagesLoader: ImagesLoader

    @BindView(R.id.create_post_top_bar)
    lateinit var topBar: TopBar

    @BindView(R.id.create_post_add_photo_button)
    lateinit var addPhotoButton: AddPhotoButton

    @BindView(R.id.create_post_image)
    lateinit var imageView: BaseImageView

    private var selectImageSourceDialog: SelectImageSourceDialog? = null

    override fun <T : LifecyclePresenter<LifecycleView>> getLifecyclePresenter() = presenter as T

    companion object {
        fun getInstance(lobbyCategoryId: Int): CreateLobbyTopicFragment {
            val args = createArgumentsBundle(lobbyCategoryId)
            val fragment = CreateLobbyTopicFragment()
            fragment.arguments = args

            return fragment
        }

        fun createArgumentsBundle(lobbyCategoryId: Int): Bundle {
            val args = Bundle()
            args.putInt(LOBBY_ROOM_KEY, lobbyCategoryId)

            return args
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent(ActivityComponent::class.java)?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_create_post, container, false)
        setUnbinder(ButterKnife.bind(this, v))
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        passArgumentsToPresenter()
        presenter.attachViewWithLifecycle(this)
        topBar.setLeftButtonClickListener { presenter.onBackClick() }
        topBar.setRightTextClickListener { presenter.onDoneClick() }
    }

    private fun passArgumentsToPresenter() {
        val lobbyCategoryId = arguments!!.getInt(LOBBY_ROOM_KEY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
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

    @OnClick(R.id.create_post_add_photo_button, R.id.create_post_image)
    fun onAddPhotoClick() {
        presenter.onAddPhotoClick()
    }

    override fun showSelectImageSourceDialog() {
        if (selectImageSourceDialog == null) {
            selectImageSourceDialog = dialogsProvider.createSelectImageSourceDialog()
        }

        selectImageSourceDialog?.show(presenter::onGalleryClick, presenter::onCameraClick)
    }

    override fun showImage(uri: Uri) {
        addPhotoButton.visibility = View.INVISIBLE
        imagesLoader.load(uri, imageView)
    }
}