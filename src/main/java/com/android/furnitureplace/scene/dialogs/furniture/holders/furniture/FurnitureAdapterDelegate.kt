package com.android.furnitureplace.scene.dialogs.furniture.holders.furniture

import android.view.ViewGroup
import com.android.furnitureplace.core.adapter.inner.InnerItemViewPresenter
import com.android.furnitureplace.core.adapter.inner.delegate.PresenterInnerAdapterDelegate

class FurnitureAdapterDelegate(
        presenter: InnerItemViewPresenter<FurnitureView>
) : PresenterInnerAdapterDelegate<FurnitureView, FurnitureViewHolder>(null, presenter) {

    override fun onCreateTypedViewHolder(parent: ViewGroup): FurnitureViewHolder = FurnitureViewHolder(parent)

}