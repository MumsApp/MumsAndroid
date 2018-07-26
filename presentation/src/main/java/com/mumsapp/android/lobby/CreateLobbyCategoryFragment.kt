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
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseFragment
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.base.LifecycleView
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.navigation.DialogsProvider
import com.mumsapp.android.product.SelectImageSourceDialog
import com.mumsapp.android.ui.views.*
import com.mumsapp.android.util.CAMERA_REQUEST_CODE
import com.mumsapp.android.util.GALLERY_REQUEST_CODE
import com.mumsapp.android.util.GOOGLE_PLACES_REQUEST_CODE
import com.mumsapp.android.util.ImagesLoader
import javax.inject.Inject

class CreateLobbyCategoryFragment : BaseFragment(), CreateLobbyCategoryView {

    @Inject
    lateinit var presenter: CreateLobbyCategoryPresenter

    @Inject
    lateinit var dialogsProvider: DialogsProvider

    @Inject
    lateinit var imagesLoader: ImagesLoader

    @BindView(R.id.create_lobby_category_top_bar)
    lateinit var topBar: TopBar

    @BindView(R.id.create_lobby_category_public_switch)
    lateinit var publicSwitch: CardSwitch

    @BindView(R.id.create_lobby_category_title_input)
    lateinit var titleInput: CardEditText

    @BindView(R.id.create_lobby_category_content_input)
    lateinit var contentInput: CardEditText

    @BindView(R.id.create_lobby_category_add_photo_button)
    lateinit var addPhotoButton: AddPhotoButton

    @BindView(R.id.create_lobby_category_image)
    lateinit var imageView: BaseImageView

    private var selectImageSourceDialog: SelectImageSourceDialog? = null

    override fun <T : LifecyclePresenter<LifecycleView>> getLifecyclePresenter() = presenter as T

    companion object {
        fun getInstance(): CreateLobbyCategoryFragment {
            return CreateLobbyCategoryFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent(ActivityComponent::class.java)?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_create_lobby_category, container, false)
        setUnbinder(ButterKnife.bind(this, v))
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachViewWithLifecycle(this)
        topBar.setLeftButtonClickListener { presenter.onBackClick() }
        topBar.setRightTextClickListener {
            presenter.onDoneClick(publicSwitch.isChecked(),
                    titleInput.getText().toString(), contentInput.getText().toString())
        }
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

    @OnClick(R.id.create_lobby_category_add_photo_button, R.id.create_lobby_category_image)
    fun onAddPhotoClick() {
        presenter.onAddPhotoClick()
    }

    @OnClick(R.id.create_lobby_category_add_members)
    fun onAddMembersClick() {
        presenter.onAddMembersClick()
    }

    override fun showSelectImageSourceDialog(galleryClickListener: () -> Unit, cameraClickListener: () -> Unit) {
        if (selectImageSourceDialog == null) {
            selectImageSourceDialog = dialogsProvider.createSelectImageSourceDialog()
        }

        selectImageSourceDialog?.show(galleryClickListener, cameraClickListener)
    }

    override fun showCoverPhoto(uri: Uri) {
        addPhotoButton.visibility = View.INVISIBLE
        imagesLoader.load(uri, imageView)
    }
}