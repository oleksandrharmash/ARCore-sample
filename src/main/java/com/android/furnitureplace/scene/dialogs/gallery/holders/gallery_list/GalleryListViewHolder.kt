package com.android.furnitureplace.scene.dialogs.gallery.holders.gallery_list

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.android.furnitureplace.R
import com.android.furnitureplace.core.adapter.holder.ViewHolder
import com.bumptech.glide.Glide

class GalleryListViewHolder(
        parent: ViewGroup
) : ViewHolder<Nothing>(parent, layoutResId = R.layout.view_gallery_list_item),
        GalleryListView {

    private val previewItem = itemView.findViewById<ImageView>(R.id.previewItem)
    private val titleItem = itemView.findViewById<TextView>(R.id.titleItem)
    private val priceItem = itemView.findViewById<TextView>(R.id.priceItem)

    override fun setPreview(path: String?) {
        Glide.with(itemView.context).load(path).into(previewItem)
    }

    override fun setTitle(title: String?) {
        titleItem.text = title
    }

    @SuppressLint("SetTextI18n")
    override fun setPrice(price: Double?) {
        priceItem.text = "$price$"
    }

}