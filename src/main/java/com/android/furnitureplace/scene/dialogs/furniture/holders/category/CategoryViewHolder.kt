package com.android.furnitureplace.scene.dialogs.furniture.holders.category

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.HORIZONTAL
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import com.android.furnitureplace.R
import com.android.furnitureplace.core.adapter.holder.ParentViewHolder
import com.android.furnitureplace.core.adapter.holder.ViewHolder
import com.android.furnitureplace.core.adapter.inner.InnerItemViewPresenter
import com.android.furnitureplace.core.adapter.inner.InnerProvider
import com.android.furnitureplace.core.adapter.inner.InnerRecyclerAdapter
import com.android.furnitureplace.scene.dialogs.furniture.holders.furniture.FurnitureAdapterDelegate
import com.android.furnitureplace.scene.dialogs.furniture.holders.furniture.FurnitureView

class CategoryViewHolder(
        parent: ViewGroup,
        provider: InnerProvider,
        presenter: InnerItemViewPresenter<FurnitureView>
) : ViewHolder<Nothing>(parent, layoutResId = R.layout.category_item_view),
        ParentViewHolder, CategoryView {

    private val categoryTitle = itemView.findViewById<TextView>(R.id.categoryTitle)
    private val recyclerView = itemView.findViewById<RecyclerView>(R.id.furnitureRecyclerView)
    private val adapter: InnerRecyclerAdapter

    init {

        recyclerView.layoutManager = LinearLayoutManager(itemView.context, HORIZONTAL, false)

        adapter = InnerRecyclerAdapter(
                this,
                null,
                provider,
                FurnitureAdapterDelegate(presenter)
        )
        recyclerView.adapter = adapter

    }

    override val parentAdapterPosition: Int get() = adapterPosition

    override fun showCategory(text: String) {
        categoryTitle?.text = text
    }
}
