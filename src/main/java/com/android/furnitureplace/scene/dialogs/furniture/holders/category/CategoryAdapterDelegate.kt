package com.android.furnitureplace.scene.dialogs.furniture.holders.category

import android.view.ViewGroup
import com.android.furnitureplace.core.adapter.ItemViewPresenter
import com.android.furnitureplace.core.adapter.delegate.PresenterAdapterDelegate
import com.android.furnitureplace.core.adapter.inner.InnerItemViewPresenter
import com.android.furnitureplace.core.adapter.inner.InnerProvider
import com.android.furnitureplace.scene.dialogs.furniture.holders.CategoryViewType
import com.android.furnitureplace.scene.dialogs.furniture.holders.furniture.FurnitureView

class CategoryAdapterDelegate(
        presenter: ItemViewPresenter<CategoryView>,
        private val innerProvider: InnerProvider,
        private val innerPresenter: InnerItemViewPresenter<FurnitureView>
) : PresenterAdapterDelegate<CategoryView, CategoryViewHolder>(
        CategoryViewType.FURNITURE,
        presenter
) {
    override fun onCreateTypedViewHolder(parent: ViewGroup): CategoryViewHolder = CategoryViewHolder(parent, innerProvider, innerPresenter)
}
