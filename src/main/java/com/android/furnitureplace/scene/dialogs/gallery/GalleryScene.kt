package com.android.furnitureplace.scene.dialogs.gallery

import com.android.furnitureplace.core.adapter.ItemViewPresenter
import com.android.furnitureplace.core.adapter.Provider
import com.android.furnitureplace.core.adapter.delegate.ViewTypeManager
import com.android.furnitureplace.core.adapter.inner.InnerItemViewPresenter
import com.android.furnitureplace.core.adapter.inner.InnerProvider
import com.android.furnitureplace.core.presentation.GenericPresenter
import com.android.furnitureplace.core.presentation.Presentable
import com.android.furnitureplace.database.RealmData
import com.android.furnitureplace.model.realm_model.Furniture
import com.android.furnitureplace.scene.dialogs.gallery.holders.gallery_item.GalleryView
import com.android.furnitureplace.scene.dialogs.gallery.holders.gallery_list.GalleryListView

interface GalleryScene {

    interface View : Presentable<View, Presenter> {

    }

    interface Presenter : GenericPresenter<View>, ViewTypeManager,
            ItemViewPresenter<GalleryView>, InnerItemViewPresenter<GalleryListView> {

        var galleryRepository: GalleryRepository

    }

    interface GalleryRepository : Provider, InnerProvider {

        val data: ArrayList<Furniture>?

        fun loadAllItem(realm: RealmData?)

    }

}