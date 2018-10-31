package com.android.furnitureplace

import android.app.Application
import com.android.furnitureplace.core.app.LayoutActivityLifecycleCallbacks
import com.android.furnitureplace.core.app.PresentableActivityLifecycleCallbacks
import com.android.furnitureplace.core.app.ScopeActivityLifecycleCallbacks
import com.android.furnitureplace.di.AppModule
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import io.realm.Realm
import timber.log.Timber
import timber.log.Timber.DebugTree
import toothpick.Toothpick
import toothpick.configuration.Configuration
import toothpick.registries.FactoryRegistryLocator
import toothpick.registries.MemberInjectorRegistryLocator


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val configuration = if (BuildConfig.DEBUG) {
            Configuration.forDevelopment().preventMultipleRootScopes()
        } else {
            Fabric.with(this, Crashlytics())
            FactoryRegistryLocator.setRootRegistry(FactoryRegistry())
            MemberInjectorRegistryLocator.setRootRegistry(MemberInjectorRegistry())
            Configuration.forProduction().disableReflection()
        }

        Toothpick.setConfiguration(configuration)
        Toothpick.openScope(this).installModules(AppModule(this))

        Timber.plant(DebugTree())
        Realm.init(this)

        registerActivityLifecycleCallbacks(ScopeActivityLifecycleCallbacks())
        registerActivityLifecycleCallbacks(PresentableActivityLifecycleCallbacks())
        registerActivityLifecycleCallbacks(LayoutActivityLifecycleCallbacks())
    }
}