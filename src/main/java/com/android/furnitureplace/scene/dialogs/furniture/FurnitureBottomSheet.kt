package com.android.furnitureplace.scene.dialogs.furniture

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.android.furnitureplace.R
import com.android.furnitureplace.core.adapter.RecyclerAdapter
import com.android.furnitureplace.scene.ar.ARFragment
import com.android.furnitureplace.scene.dialogs.BaseBottomSheetDialog
import com.android.furnitureplace.scene.dialogs.furniture.holders.category.CategoryAdapterDelegate
import kotlinx.android.synthetic.main.dialog_furniture.*

class FurnitureBottomSheet : BaseBottomSheetDialog() {

    // region Lifecycle

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.dialog_furniture, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val parentFrag = this@FurnitureBottomSheet.targetFragment as ARFragment
        categoryRecyclerView.layoutManager = LinearLayoutManager(context)
        categoryRecyclerView.adapter = RecyclerAdapter(
                parentFrag.presenter.furnitureRepository,
                parentFrag.presenter,
                CategoryAdapterDelegate(parentFrag.presenter, parentFrag.presenter.furnitureRepository, parentFrag.presenter)
        )
    }

    // endregion

}