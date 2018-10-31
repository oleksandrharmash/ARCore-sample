package com.android.furnitureplace.database

import com.android.furnitureplace.core.common.MultiMap
import com.android.furnitureplace.model.ItemStore
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.io.File


object FireBaseData {

    private val database = FirebaseDatabase.getInstance().reference
    private var storage = FirebaseStorage.getInstance()

    private var map: MultiMap<String, ItemStore> = MultiMap()
    private val keys = arrayListOf<String>()
    private val furniture = mutableSetOf<ItemStore>()

    fun getData(data: (map: HashMap<String, ArrayList<ItemStore>>) -> Unit) {
        if (map.isEmpty()) {
            getCategory {
                keys.forEach { key ->
                    if (furniture.any { it.category == key }) {
                        map.putMultiValue(key, ArrayList(furniture.filter { it.category == key }))
                    }
                }
                data(map.getAllValues())
            }
        } else {
            data(map.getAllValues())
        }
    }


    private fun getCategory(dataGetting: () -> Unit) {
        database.child("Categories").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {
                    it.key?.let { key -> keys.add(key) }
                }
                getFurniture {
                    dataGetting()
                }
            }
        })
    }

    private fun getFurniture(dataGetting: () -> Unit) {
        database.child("Furniture").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach { dataSnapshotItem ->
                    dataSnapshotItem.getValue(ItemStore::class.java)?.let {
                        if (!it.sourceAndroid.isNullOrEmpty() && !it.preview.isNullOrEmpty()) {
                            furniture.add(it)
                        }
                    }
                }
                dataGetting()
            }
        })
    }

    fun downloadFile(file: File, reference: String, callback: (path: String) -> Unit) {
        if (!file.exists()) {
            val httpsReference = storage.getReferenceFromUrl(reference)
            httpsReference.getFile(file).addOnSuccessListener {
                callback(file.absolutePath)
            }.addOnFailureListener {

            }
        } else {
            callback(file.absolutePath)
        }
    }

}