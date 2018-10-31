package com.android.furnitureplace.scene.dialogs.furniture.holders.furniture

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.android.furnitureplace.R
import com.android.furnitureplace.core.adapter.holder.ViewHolder
import com.bumptech.glide.Glide

class FurnitureViewHolder(
        parent: ViewGroup
) : ViewHolder<Nothing>(parent, layoutResId = R.layout.view_furniture_item),
        FurnitureView {

    private val furnitureImage = itemView.findViewById<ImageView>(R.id.furnitureImage)
    private val furnitureTitle = itemView.findViewById<TextView>(R.id.furnitureTitle)
    private val furnitureType = itemView.findViewById<TextView>(R.id.furnitureType)
    private val furniturePrice = itemView.findViewById<TextView>(R.id.furniturePrice)

    override fun showImage(url: String) {
        Glide.with(context).load(url).into(furnitureImage)
    }

    override fun showTitle(title: String) {
        furnitureTitle.text = title
    }

    override fun showType(type: String) {
        furnitureType.text = type
    }

    @SuppressLint("SetTextI18n")
    override fun showPrice(price: String) {
        furniturePrice.text = "$price$"
    }

    override fun showButtons(action: () -> Unit) {
        itemView.setOnClickListener {
            action()
        }
    }
}