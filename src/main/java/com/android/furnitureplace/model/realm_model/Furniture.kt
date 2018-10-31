package com.android.furnitureplace.model.realm_model

import io.realm.RealmList
import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.io.Serializable
import java.util.*


@RealmClass
open class Furniture : RealmModel, Serializable {

    @PrimaryKey
    var id: String = UUID.randomUUID().toString()

    var preview: String? = null

    var listItem: RealmList<FurnitureItem>? = null

}