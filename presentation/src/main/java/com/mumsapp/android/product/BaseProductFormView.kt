package com.mumsapp.android.product

import android.net.Uri
import com.mumsapp.android.base.LifecycleView

interface BaseProductFormView : LifecycleView {

    fun showProductCategory(name: String)

    fun showSelectImageSourceDialog(galleryClickListener: () -> Unit, cameraClickListener: () -> Unit)

    fun showAddPhotoHeader()

    fun showImageHeader(uri: Uri)

    fun showImageHeader(apiUrl: String)

    fun hideImageSlider()

    fun showImageSlider(items: List<ImageSliderItem>,  deleteButtonClickListener: ((position: Int) -> Unit))

    fun addImageSliderItem(items: List<ImageSliderItem>, changedItemPosition: Int)

    fun removeImageSliderItem(items: List<ImageSliderItem>, changedItemPosition: Int)

    fun showConfirmationDialog(avatarPath: String?, avatarTitle: String, title: String,
                                        description: String?, confirmButtonText: String, confirmButtonClick: () -> Unit,
                                        cancelButtonText: String, cancelButtonClick: () -> Unit)

    fun showEditLocationScreen()

    fun showNewLocation(latitude: Double, longitude: Double, name: String)
}