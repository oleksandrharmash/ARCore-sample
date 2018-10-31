package com.android.furnitureplace.model.realm_model

import io.realm.RealmModel
import io.realm.annotations.RealmClass
import java.io.Serializable

@RealmClass
open class FurnitureItem : RealmModel, Serializable {
    var preview: String? = null
    var price: Double? = null
    var category: String? = null
    var title: String? = null
}