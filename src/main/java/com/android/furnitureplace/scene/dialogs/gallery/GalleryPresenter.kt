package com.android.furnitureplace.scene.dialogs.gallery

import com.android.furnitureplace.core.adapter.delegate.ViewType
import com.android.furnitureplace.presentation.BaseViewPresenter
import com.android.furnitureplace.scene.dialogs.gallery.holders.GalleryViewType
import com.android.furnitureplace.scene.dialogs.gallery.holders.gallery_item.GalleryView
import com.android.furnitureplace.scene.dialogs.gallery.holders.gallery_list.GalleryListView
import javax.inject.Inject

class GalleryPresenter @Inject constructor(
        view: GalleryScene.View,
        override var galleryRepository: GalleryScene.GalleryRepository
) : BaseViewPresenter<GalleryScene.View>(view), GalleryScene.Presenter {

    override fun getItemViewType(position: Int): ViewType = GalleryViewType.GALLERY_ITEM

    override fun present(view: GalleryView, position: Int) {
        view.setPreview(galleryRepository.data?.get(position)?.preview)

        var data = 0.0
        galleryRepository.data?.get(position)?.listItem?.forEach { item ->
            item.price?.let { data += it }
        }

        view.setAllPrice("%.2f".format(data))
    }

    override fun present(elementView: GalleryListView, parentPosition: Int, position: Int) {
        val element = galleryRepository.data?.get(parentPosition)?.listItem?.get(position)
        elementView.setPreview(element?.preview)
        elementView.setTitle(element?.title)
        elementView.setPrice(element?.price)
    }
}