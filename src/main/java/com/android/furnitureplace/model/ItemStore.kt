package com.android.furnitureplace.model

import android.os.Parcel
import android.os.Parcelable

data class ItemStore(
		val preview: String? = null,
		val price: Double? = null,
		val sourceAndroid: String? = null,
		val sourcesURL: String? = null,
		val category: String? = null,
		val title: String? = null,
		val brand: String? = null,
		val updated: Int? = null
) : Parcelable {
	constructor(source: Parcel) : this(
			source.readString(),
			source.readValue(Double::class.java.classLoader) as Double?,
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readValue(Int::class.java.classLoader) as Int?
	)

	override fun describeContents() = 0

	override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
		writeString(preview)
		writeValue(price)
		writeString(sourceAndroid)
		writeString(sourcesURL)
		writeString(category)
		writeString(title)
		writeString(brand)
		writeValue(updated)
	}

	companion object {
		@JvmField
		val CREATOR: Parcelable.Creator<ItemStore> = object : Parcelable.Creator<ItemStore> {
			override fun createFromParcel(source: Parcel): ItemStore = ItemStore(source)
			override fun newArray(size: Int): Array<ItemStore?> = arrayOfNulls(size)
		}
	}
}
