package com.android.furnitureplace.scene.dialogs.gallery.holders.gallery_item

import com.android.furnitureplace.core.presentation.ItemView

interface GalleryView : ItemView {

    fun setPreview(path: String?)

    fun setAllPrice(price: String)

}