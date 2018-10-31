package com.android.furnitureplace.scene.ar

import com.android.furnitureplace.core.adapter.delegate.ViewType
import com.android.furnitureplace.model.ItemStore
import com.android.furnitureplace.model.realm_model.FurnitureItem
import com.android.furnitureplace.presentation.BaseViewPresenter
import com.android.furnitureplace.scene.dialogs.furniture.holders.CategoryViewType
import com.android.furnitureplace.scene.dialogs.furniture.holders.category.CategoryView
import com.android.furnitureplace.scene.dialogs.furniture.holders.furniture.FurnitureView
import io.realm.RealmList
import javax.inject.Inject

class ARPresenter @Inject constructor(
        view: ARScene.View,
        override var furnitureRepository: ARScene.FurnitureRepository
) : BaseViewPresenter<ARScene.View>(view), ARScene.Presenter {

    override fun start() {
        super.start()
        furnitureRepository.loadAllFurniture()
    }

    override fun getItemViewType(position: Int): ViewType = CategoryViewType.CATEGORY

    override fun present(view: CategoryView, position: Int) {
        (view).showCategory(furnitureRepository.data[position])
    }

    override fun present(elementView: FurnitureView, parentPosition: Int, position: Int) {
        val element = furnitureRepository.data.getValues(furnitureRepository.data[parentPosition])?.get(position)
                ?: return
        elementView.showImage(element.preview ?: "")
        elementView.showTitle(element.title ?: "")
        elementView.showType(element.category ?: "")
        elementView.showPrice(element.price.toString())
        elementView.showButtons {
            view?.show(element)
        }
    }

    override fun getRealmModel(furnitureList: ArrayList<ItemStore>): RealmList<FurnitureItem> {
        val listFurniture = RealmList<FurnitureItem>()
        furnitureList.forEach {
            val item = FurnitureItem()
            item.preview = it.preview
            item.category = it.category
            item.price = it.price
            item.title = it.title
            listFurniture.add(item)
        }
        return listFurniture
    }
}