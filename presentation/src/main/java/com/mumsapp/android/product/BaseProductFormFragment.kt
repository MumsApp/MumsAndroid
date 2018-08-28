package com.mumsapp.android.product

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View
import butterknife.BindView
import butterknife.OnClick
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseFragment
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.base.LifecycleView
import com.mumsapp.android.common.dialogs.ConfirmationWithAvatarDialog
import com.mumsapp.android.navigation.ActivitiesNavigationService
import com.mumsapp.android.navigation.DialogsProvider
import com.mumsapp.android.ui.views.BaseImageView
import com.mumsapp.android.ui.views.BaseTextView
import com.mumsapp.android.ui.views.HorizontalRecyclerView
import com.mumsapp.android.ui.views.TopBar
import com.mumsapp.android.ui.widgets.EditProductDetailsWidget
import com.mumsapp.android.ui.widgets.LocationWidget
import com.mumsapp.android.util.ImagesLoader
import com.mumsapp.domain.utils.CAMERA_REQUEST_CODE
import com.mumsapp.domain.utils.GALLERY_REQUEST_CODE
import com.mumsapp.domain.utils.GOOGLE_PLACES_REQUEST_CODE
import javax.inject.Inject

abstract class BaseProductFormFragment : BaseFragment(), BaseProductFormView {

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

    @BindView(R.id.add_product_product_details_widget)
    lateinit var editProductDetailsWidget: EditProductDetailsWidget

    @BindView(R.id.add_product_location_widget)
    lateinit var locationWidget: LocationWidget

    private var selectImageSourceDialog: SelectImageSourceDialog? = null

    private var confirmationWithAvatarDialog: ConfirmationWithAvatarDialog? = null

    override fun <T : LifecyclePresenter<LifecycleView>> getLifecyclePresenter() = getFormPresenter() as T
    
    abstract fun getFormPresenter(): BaseProductFormPresenter<BaseProductFormView>

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    getFormPresenter().onGalleryImageReceived(data!!.data)
                }

                CAMERA_REQUEST_CODE -> {
                    getFormPresenter().onCameraImageReceived()
                }

                GOOGLE_PLACES_REQUEST_CODE -> {
                    val place = PlaceAutocomplete.getPlace(context, data)
                    getFormPresenter().onLocationSelected(place)
                }
            }
        }
    }

    @OnClick(R.id.add_product_add_image_button, R.id.add_product_add_image_text,
            R.id.add_product_add_image_background)
    fun onAddPhotoClick() {
        getFormPresenter().onAddPhotoClick()
    }

    @OnClick(R.id.add_product_upload_button)
    fun onUploadButtonClick() {
        getFormPresenter().onUploadButtonClick(editProductDetailsWidget.getTitle(),
                editProductDetailsWidget.getPrice(), editProductDetailsWidget.getDescription().toString())
    }

    override fun showProductCategory(name: String) {
        editProductDetailsWidget.setCategory(name)
    }

    override fun showSelectImageSourceDialog(galleryClickListener: () -> Unit, cameraClickListener: () -> Unit) {
        if (selectImageSourceDialog == null) {
            selectImageSourceDialog = dialogsProvider.createSelectImageSourceDialog()
        }

        selectImageSourceDialog?.show(galleryClickListener, cameraClickListener)
    }

    override fun hideImageSlider() {
        photosLabelView.visibility = View.GONE
        photosRecyclerView.visibility = View.GONE
        photosRecyclerView.adapter = null
    }

    override fun showAddPhotoHeader() {
        headerImageView.visibility = View.INVISIBLE
    }

    override fun showImageHeader(uri: Uri) {
        headerImageView.visibility = View.VISIBLE
        imagesLoader.load(uri, headerImageView)
    }

    override fun showImageHeader(apiUrl: String) {
        headerImageView.visibility = View.VISIBLE
        imagesLoader.loadFromApiPath(apiUrl, headerImageView)
    }

    override fun showImageSlider(items: List<ImageSliderItem>, deleteButtonClickListener: ((position: Int) -> Unit)) {
        photosAdapter.items = items
        photosAdapter.deleteButtonClickListener = deleteButtonClickListener
        photosAdapter.setItemsClickListener(getFormPresenter()::onPhotoSliderItemClick)

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

    override fun showConfirmationDialog(avatarPath: String?, avatarTitle: String, title: String, 
                                        description: String?, confirmButtonText: String, confirmButtonClick: () -> Unit,
                                        cancelButtonText: String, cancelButtonClick: () -> Unit) {
        if (confirmationWithAvatarDialog == null) {
            confirmationWithAvatarDialog = dialogsProvider.createConfirmationWithAvatarDialog()
        }

        confirmationWithAvatarDialog!!.show(avatarPath, avatarTitle, title, description,
                confirmButtonText, cancelButtonText, confirmButtonClick, cancelButtonClick)
    }

    override fun showEditLocationScreen() {
        activitiesNavigationService.openGooglePlacesOverlayActivity(this, GOOGLE_PLACES_REQUEST_CODE)
    }

    override fun showNewLocation(latitude: Double, longitude: Double, name: String) {
        locationWidget.setMapCoordinates(latitude, longitude)
        locationWidget.setLocationName(name)
    }
}