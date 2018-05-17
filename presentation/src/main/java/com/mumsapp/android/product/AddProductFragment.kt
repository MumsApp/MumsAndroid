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
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseFragment
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.base.LifecycleView
import com.mumsapp.android.common.dialogs.ConfirmationWithAvatarDialog
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.navigation.ActivitiesNavigationService
import com.mumsapp.android.navigation.DialogsProvider
import com.mumsapp.android.ui.views.BaseImageView
import com.mumsapp.android.ui.views.BaseTextView
import com.mumsapp.android.ui.views.HorizontalRecyclerView
import com.mumsapp.android.ui.views.TopBar
import com.mumsapp.android.ui.widgets.LocationWidget
import com.mumsapp.android.util.CAMERA_REQUEST_CODE
import com.mumsapp.android.util.GALLERY_REQUEST_CODE
import com.mumsapp.android.util.GOOGLE_PLACES_REQUEST_CODE
import com.mumsapp.android.util.ImagesLoader
import javax.inject.Inject

class AddProductFragment : BaseFragment(), AddProductView {

    @Inject
    lateinit var presenter: AddProductPresenter

    @Inject
    lateinit var dialogsProvider: DialogsProvider

    @Inject
    lateinit var imagesLoader: ImagesLoader

    @Inject
    lateinit var photosAdapter: ProductImagesAdapter

    @Inject
    lateinit var activitiesNavigationService: ActivitiesNavigationService

    @BindView(R.id.add_product_top_bar)
    lateinit var topBar: TopBar

    @BindView(R.id.add_product_image_header)
    lateinit var headerImageView: BaseImageView

    @BindView(R.id.add_product_upload_photos_label)
    lateinit var photosLabelView: BaseTextView

    @BindView(R.id.add_product_photos_recycler)
    lateinit var photosRecyclerView: HorizontalRecyclerView

    @BindView(R.id.add_product_location_widget)
    lateinit var locationWidget: LocationWidget

    private var selectImageSourceDialog: SelectImageSourceDialog? = null

    private var confirmationWithAvatarDialog: ConfirmationWithAvatarDialog? = null

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
        locationWidget.setWidgetButtonListener { presenter.onEditLocationClick() }
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

                GOOGLE_PLACES_REQUEST_CODE -> {
                    val place = PlaceAutocomplete.getPlace(context, data)
                    presenter.onLocationSelected(place)
                }
            }
        }
    }

    @OnClick(R.id.add_product_add_image_button, R.id.add_product_add_image_text,
            R.id.add_product_add_image_background)
    fun onAddPhotoClick() {
        presenter.onAddPhotoClick()
    }

    @OnClick(R.id.add_product_upload_button)
    fun onUploadButtonClick() {
        presenter.onUploadButtonClick()
    }

    override fun showSelectImageSourceDialog() {
        if (selectImageSourceDialog == null) {
            selectImageSourceDialog = dialogsProvider.createSelectImageSourceDialog()
        }

        selectImageSourceDialog?.show(presenter::onGalleryClick, presenter::onCameraClick)
    }

    override fun hideImageSlider() {
        photosLabelView.visibility = View.GONE
        photosRecyclerView.visibility = View.GONE
        photosRecyclerView.adapter = null
    }

    override fun showImageHeader(uri: Uri) {
        imagesLoader.load(uri, headerImageView)
    }

    override fun showImageSlider(items: List<ImageSliderItem>, deleteButtonClickListener: ((position: Int) -> Unit)) {
        photosAdapter.items = items
        photosAdapter.deleteButtonClickListener = deleteButtonClickListener
        photosAdapter.setItemsClickListener(presenter::onPhotoSliderItemClick)

        if (photosRecyclerView.adapter == null) {
            photosRecyclerView.adapter = photosAdapter
        }

        photosLabelView.visibility = View.VISIBLE
        photosRecyclerView.visibility = View.VISIBLE
    }

    override fun addImageSliderItem(items: List<ImageSliderItem>, changedItemPosition: Int) {
        photosAdapter.items = items
        photosAdapter.notifyItemInserted(changedItemPosition)
    }

    override fun removeImageSliderItem(items: List<ImageSliderItem>, changedItemPosition: Int) {
        photosAdapter.items = items
        photosAdapter.notifyItemRemoved(changedItemPosition)
    }

    override fun showConfirmationDialog(avatarUri: Uri?, avatarTitle: String, title: String, description: String?, confirmButtonText: String, cancelButtonText: String) {
        if (confirmationWithAvatarDialog == null) {
            confirmationWithAvatarDialog = dialogsProvider.createConfirmationWithAvatarDialog()
        }

        confirmationWithAvatarDialog!!.show(avatarUri, avatarTitle, title, description,
                confirmButtonText, cancelButtonText, presenter::onConfirmDialogButtonClick,
                presenter::onCancelDialogButtonClick)
    }

    override fun showEditLocationScreen() {
        activitiesNavigationService.openGooglePlacesOverlayActivity(this, GOOGLE_PLACES_REQUEST_CODE)
    }

    override fun showNewLocation(latitude: Double, longitude: Double, name: String) {
        locationWidget.setMapCoordinates(latitude, longitude)
        locationWidget.setLocationName(name)
    }
}