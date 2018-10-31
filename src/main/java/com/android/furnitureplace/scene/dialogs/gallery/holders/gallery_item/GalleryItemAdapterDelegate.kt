package com.android.furnitureplace.scene.dialogs.gallery.holders.gallery_item

import android.view.ViewGroup
import com.android.furnitureplace.core.adapter.ItemViewPresenter
import com.android.furnitureplace.core.adapter.delegate.PresenterAdapterDelegate
import com.android.furnitureplace.core.adapter.inner.InnerItemViewPresenter
import com.android.furnitureplace.core.adapter.inner.InnerProvider
import com.android.furnitureplace.scene.dialogs.gallery.holders.GalleryViewType
import com.android.furnitureplace.scene.dialogs.gallery.holders.gallery_list.GalleryListView

class GalleryItemAdapterDelegate(
        presenter: ItemViewPresenter<GalleryView>,
        private val innerProvider: InnerProvider,
        private val innerPresenter: InnerItemViewPresenter<GalleryListView>
) : PresenterAdapterDelegate<GalleryView, GalleryViewHolder>(
        GalleryViewType.GALLERY_LIST,
        presenter
) {
    override fun onCreateTypedViewHolder(parent: ViewGroup): GalleryViewHolder = GalleryViewHolder(parent, innerProvider, innerPresenter)
}