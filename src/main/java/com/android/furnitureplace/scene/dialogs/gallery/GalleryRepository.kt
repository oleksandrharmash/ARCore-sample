package com.android.furnitureplace.scene.dialogs.gallery

import com.android.furnitureplace.database.RealmData
import com.android.furnitureplace.model.realm_model.Furniture
import javax.inject.Inject

class GalleryRepository @Inject constructor() : GalleryScene.GalleryRepository {

    override var data: ArrayList<Furniture>? = arrayListOf()

    override var itemCount: Int =
            data?.size ?: 0

    override fun loadAllItem(realm: RealmData?) {
        realm?.getAll()?.let { data?.addAll(it) }
        data?.reverse()
        itemCount = data?.size ?: 0
    }

    override fun getItemCount(parentPosition: Int): Int =
            data?.get(parentPosition)?.listItem?.size ?: 0

    override fun getItemTop(parentPosition: Int): Int {
        data?.get(parentPosition)?.listItem?.forEachIndexed { index, _ ->
            return index
        }
        return 0
    }

}