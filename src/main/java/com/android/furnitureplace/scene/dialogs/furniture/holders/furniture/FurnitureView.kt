package com.android.furnitureplace.scene.dialogs.furniture.holders.furniture

import com.android.furnitureplace.core.presentation.ItemView

interface FurnitureView : ItemView {

    fun showImage(url: String)

    fun showTitle(title: String)

    fun showType(type: String)

    fun showPrice(price: String)

    fun showButtons(action: () -> Unit)

}