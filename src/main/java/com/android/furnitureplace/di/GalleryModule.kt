package com.android.furnitureplace.di

import com.android.furnitureplace.scene.dialogs.gallery.*
import toothpick.config.Module

class GalleryModule(fragment: GalleryBottomSheet) : Module() {

    init {
        bind(GalleryScene.View::class.java).toInstance(fragment)
        bind(GalleryScene.Presenter::class.java).to(GalleryPresenter::class.java)
                .singletonInScope()
        bind(GalleryScene.GalleryRepository::class.java).to(GalleryRepository::class.java)
                .singletonInScope()
    }

}
