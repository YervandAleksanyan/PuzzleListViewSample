package com.example.yervand.puzzlelistviewsample.view

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.yervand.puzzlelistviewsample.view.managers.TextLayoutManager

class CustomItemDecoration(private val manager: TextLayoutManager) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        with(outRect) {
            val position = parent.getChildAdapterPosition(view)
            val item = manager.items[position]
            val spannableModel = manager.getSpannableStringModelForTextEntity(position, view)
            if (!item.isParagraphStart)
                top = spannableModel.topOffset
        }
    }
}
