package com.mumsapp.android.product

import android.net.Uri
import com.mumsapp.android.base.LifecycleView

interface AddProductView : LifecycleView {

    fun showSelectImageSourceDialog()

    fun showImageHeader(uri: Uri)

    fun hideImageSlider()

    fun showImageSlider(items: List<ImageSliderItem>,  deleteButtonClickListener: ((position: Int) -> Unit))

    fun addImageSliderItem(items: List<ImageSliderItem>, changedItemPosition: Int)

    fun removeImageSliderItem(items: List<ImageSliderItem>, changedItemPosition: Int)

    fun showConfirmationDialog(avatarUri: Uri?, avatarTitle: String?, title: String, description: String?,
                               confirmButtonText: String, cancelButtonText: String)
}