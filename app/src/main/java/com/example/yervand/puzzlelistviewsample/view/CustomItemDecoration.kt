package com.example.yervand.puzzlelistviewsample.view

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.yervand.puzzlelistviewsample.R
import com.example.yervand.puzzlelistviewsample.db.model.TextEntity

class CustomItemDecoration(val dataSet: List<TextEntity>) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        with(outRect) {
            if (!dataSet[parent.getChildAdapterPosition(view)].isParagraphStart)
                top = -(view.findViewById<TextView>(R.id.text)).lineHeight
        }
    }

}
