package com.android.furnitureplace.scene.dialogs

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialogFragment
import android.support.design.widget.CoordinatorLayout
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import com.android.furnitureplace.R

open class BaseBottomSheetDialog : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog

        if (dialog != null) {
            val bottomSheet = dialog.findViewById<View>(R.id.design_bottom_sheet)
            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }
        val view = view
        view?.post {
            val parent = view.parent as View
            val params = (parent).layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior
            val bottomSheetBehavior = behavior as BottomSheetBehavior
            bottomSheetBehavior.peekHeight = (view.measuredHeight * 0.9f).toInt()
            parent.setBackgroundColor(Color.WHITE)
        }
    }

    fun getDisplayMetrics(): DisplayMetrics {
        val dm = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(dm)
        return dm
    }

}