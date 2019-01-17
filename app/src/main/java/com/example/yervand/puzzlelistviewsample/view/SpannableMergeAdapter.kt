package com.example.yervand.puzzlelistviewsample.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.yervand.puzzlelistviewsample.R
import com.example.yervand.puzzlelistviewsample.TextLayoutManager

class SpannableMergeAdapter(val textLayoutManager: TextLayoutManager) :
    RecyclerView.Adapter<SpannableViewHolder>() {

    enum class ScrollDirection(var value: Int) {
        UP(1),
        DOWN(-1)
    }

    private var leadingSpace = 0
    private var trailingSpace = 0

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): SpannableViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.cell_text, viewGroup, false)
        return SpannableViewHolder(view)
    }

    override fun getItemCount(): Int = textLayoutManager.manager.getAll().size

    override fun onBindViewHolder(viewHolder: SpannableViewHolder, position: Int) {
        viewHolder.textView.text = textLayoutManager.getSpannableStringForTextEntity(position, viewHolder.textView)
    }
}