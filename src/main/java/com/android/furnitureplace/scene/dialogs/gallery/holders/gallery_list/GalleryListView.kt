package com.android.furnitureplace.scene.dialogs.gallery.holders.gallery_list

import com.android.furnitureplace.core.presentation.ItemView

interface GalleryListView : ItemView {

    fun setPreview(path: String?)

    fun setTitle(title: String?)

    fun setPrice(price: Double?)

}