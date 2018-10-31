package com.android.furnitureplace.scene.dialogs.gallery

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.furnitureplace.R
import com.android.furnitureplace.core.adapter.RecyclerAdapter
import com.android.furnitureplace.core.di.ModulesInstallable
import com.android.furnitureplace.database.RealmData
import com.android.furnitureplace.di.GalleryModule
import com.android.furnitureplace.scene.dialogs.BaseBottomSheetDialog
import com.android.furnitureplace.scene.dialogs.gallery.holders.gallery_item.GalleryItemAdapterDelegate
import kotlinx.android.synthetic.main.dialog_gallery.*
import toothpick.Scope
import javax.inject.Inject


class GalleryBottomSheet : BaseBottomSheetDialog(), ModulesInstallable, GalleryScene.View {

    @Inject
    override lateinit var presenter: GalleryScene.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.galleryRepository.loadAllItem(RealmData(context))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.dialog_gallery, container, false)

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        galleryRecyclerView.layoutManager = LinearLayoutManager(context)

        galleryRecyclerView.adapter = RecyclerAdapter(
                presenter.galleryRepository,
                presenter,
                GalleryItemAdapterDelegate(presenter, presenter.galleryRepository, presenter)
        )
    }

    override fun installModules(scope: Scope) {
        scope.installModules(GalleryModule(this))
    }

}
