package com.android.furnitureplace.scene.dialogs.gallery.holders.gallery_list

import android.view.ViewGroup
import com.android.furnitureplace.core.adapter.inner.InnerItemViewPresenter
import com.android.furnitureplace.core.adapter.inner.delegate.PresenterInnerAdapterDelegate

class GalleryListAdapterDelegate(
        presenter: InnerItemViewPresenter<GalleryListView>
) : PresenterInnerAdapterDelegate<GalleryListView, GalleryListViewHolder>(null, presenter) {

    override fun onCreateTypedViewHolder(parent: ViewGroup): GalleryListViewHolder = GalleryListViewHolder(parent)
}