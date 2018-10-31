package com.android.furnitureplace.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import toothpick.config.Module

class AppModule(context: Context) : Module() {

    init {
        bind(Context::class.java).toInstance(context)
        bind(RequestManager::class.java).toInstance(Glide.with(context))
    }

}

