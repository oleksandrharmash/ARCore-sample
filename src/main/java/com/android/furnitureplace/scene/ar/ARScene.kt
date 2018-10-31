package com.android.furnitureplace.scene.ar

import com.android.furnitureplace.core.adapter.DataProvider
import com.android.furnitureplace.core.adapter.ItemViewPresenter
import com.android.furnitureplace.core.adapter.delegate.ViewTypeManager
import com.android.furnitureplace.core.adapter.inner.InnerItemViewPresenter
import com.android.furnitureplace.core.adapter.inner.InnerProvider
import com.android.furnitureplace.core.presentation.GenericPresenter
import com.android.furnitureplace.core.presentation.Presentable
import com.android.furnitureplace.model.ItemStore
import com.android.furnitureplace.model.realm_model.FurnitureItem
import com.android.furnitureplace.scene.dialogs.furniture.holders.category.CategoryView
import com.android.furnitureplace.scene.dialogs.furniture.holders.furniture.FurnitureView
import io.realm.RealmList
import java.io.File

interface ARScene {

    interface View : Presentable<View, Presenter> {
        fun show(element: ItemStore)
    }

    interface Presenter : GenericPresenter<View>, ViewTypeManager,
            ItemViewPresenter<CategoryView>, InnerItemViewPresenter<FurnitureView> {
        var furnitureRepository: FurnitureRepository

        fun getRealmModel(furnitureList: ArrayList<ItemStore>): RealmList<FurnitureItem>
    }

    interface FurnitureRepository : DataProvider<String, ItemStore>, InnerProvider {

        var hasRecovery: Boolean

        fun loadAllFurniture()

        fun downloadModel(file: File, reference: String, callback: (path: String) -> Unit)

    }

}