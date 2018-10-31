package com.android.furnitureplace.di

import com.android.furnitureplace.scene.ar.*
import toothpick.config.Module

class ARModule(fragment: ARFragment) : Module() {

    init {
        bind(ARScene.View::class.java).toInstance(fragment)
        bind(ARScene.Presenter::class.java).to(ARPresenter::class.java)
                .singletonInScope()
        bind(ARScene.FurnitureRepository::class.java).to(FurnitureRepository::class.java)
                .singletonInScope()
    }

}