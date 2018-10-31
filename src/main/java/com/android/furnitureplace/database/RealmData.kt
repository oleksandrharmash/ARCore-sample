package com.android.furnitureplace.database

import android.content.Context
import com.android.furnitureplace.model.realm_model.Furniture
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.deleteFromRealm


private const val REALM_NAME = "furnitureplace.realm"

class RealmData constructor(val context: Context?) {

    private var config: RealmConfiguration? = null

    init {
        if (context != null) {
            Realm.init(context)
            config = RealmConfiguration.Builder()
                    .name(REALM_NAME)
                    .schemaVersion(1)
                    .build()
        }
    }

    fun add(item: Furniture) {
        val realm = config?.let { Realm.getInstance(it) }
        realm?.beginTransaction()
        realm?.copyToRealm(item)
        realm?.commitTransaction()
    }

    fun remove(item: Furniture) {
        val realm = config?.let { Realm.getInstance(it) }
        realm?.beginTransaction()
        item.deleteFromRealm()
        realm?.commitTransaction()
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    fun getAll(): ArrayList<Furniture> =
            ArrayList(config?.let { Realm.getInstance(it) }?.where(Furniture::class.java)?.findAll())

}