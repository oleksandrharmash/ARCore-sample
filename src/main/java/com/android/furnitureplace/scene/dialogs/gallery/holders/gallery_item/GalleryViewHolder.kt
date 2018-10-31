package com.android.furnitureplace.scene.dialogs.gallery.holders.gallery_item

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.android.furnitureplace.R
import com.android.furnitureplace.core.adapter.holder.ParentViewHolder
import com.android.furnitureplace.core.adapter.holder.ViewHolder
import com.android.furnitureplace.core.adapter.holder.swipe.OnSwipeListener
import com.android.furnitureplace.core.adapter.holder.swipe.SwipeLayout
import com.android.furnitureplace.core.adapter.inner.InnerItemViewPresenter
import com.android.furnitureplace.core.adapter.inner.InnerProvider
import com.android.furnitureplace.core.adapter.inner.InnerRecyclerAdapter
import com.android.furnitureplace.scene.dialogs.gallery.holders.gallery_list.GalleryListAdapterDelegate
import com.android.furnitureplace.scene.dialogs.gallery.holders.gallery_list.GalleryListView
import com.bumptech.glide.Glide

class GalleryViewHolder(
        parent: ViewGroup,
        provider: InnerProvider,
        presenter: InnerItemViewPresenter<GalleryListView>
) : ViewHolder<Nothing>(parent, layoutResId = R.layout.view_gallery_item),
        ParentViewHolder, GalleryView {

    private val galleryList = itemView.findViewById<RecyclerView>(R.id.galleryList)
    private val preview = itemView.findViewById<ImageView>(R.id.preview)
    private val allPrice = itemView.findViewById<TextView>(R.id.allPrice)
    private val swipeLayout = itemView.findViewById<SwipeLayout>(R.id.swipe_layout)
    private val rightView = itemView.findViewById<View>(R.id.left_view)

    init {
        galleryList.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)

        galleryList.adapter = InnerRecyclerAdapter(
                this,
                null,
                provider,
                GalleryListAdapterDelegate(presenter))

        val onClick = View.OnClickListener { swipeLayout.animateReset() }

        if (rightView != null) {
            rightView.isClickable = true
            rightView.setOnClickListener(onClick)
        }

        swipeLayout.setOnSwipeListener(object : OnSwipeListener {
            override fun onBeginSwipe(swipeLayout: SwipeLayout, moveToRight: Boolean) {

            }

            override fun onSwipeClampReached(swipeLayout: SwipeLayout, moveToRight: Boolean) {
            }

            override fun onLeftStickyEdge(swipeLayout: SwipeLayout, moveToRight: Boolean) {
            }

            override fun onRightStickyEdge(swipeLayout: SwipeLayout, moveToRight: Boolean) {
            }
        })
    }

    override fun setPreview(path: String?) {
        Glide.with(itemView.context).load(path).into(preview)
    }

    @SuppressLint("SetTextI18n")
    override fun setAllPrice(price: String) {
        allPrice.text = "$$price"
    }

    override val parentAdapterPosition: Int get() = adapterPosition

}