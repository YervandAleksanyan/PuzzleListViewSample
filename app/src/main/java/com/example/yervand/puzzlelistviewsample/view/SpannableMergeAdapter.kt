package com.example.yervand.puzzlelistviewsample.view

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.yervand.puzzlelistviewsample.R
import com.example.yervand.puzzlelistviewsample.view.managers.TextLayoutManager
import kotlinx.android.synthetic.main.cell_text.view.*


class SpannableMergeAdapter(val textLayoutManager: TextLayoutManager) :
    RecyclerView.Adapter<SpannableViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): SpannableViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.cell_text, viewGroup, false)
        return SpannableViewHolder(view)
    }

    override fun getItemCount(): Int = textLayoutManager.items.size

    override fun onBindViewHolder(viewHolder: SpannableViewHolder, position: Int) {
        viewHolder.textView.text =
                textLayoutManager.getSpannableStringModelForTextEntity(position, viewHolder.textView).spannableString
        viewHolder.textView.setOnClickListener {
            Log.i("tag", it.text.text.toString().substring(0, 2))
        }
    }

    private fun isViewOverlapping(firstView: View, secondView: View): Boolean {
        val firstPosition = IntArray(2)
        val secondPosition = IntArray(2)

        firstView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        firstView.getLocationOnScreen(firstPosition)
        secondView.getLocationOnScreen(secondPosition)

        val r = firstView.measuredWidth + firstPosition[0]
        val l = secondPosition[0]
        return r >= l && r != 0 && l != 0
    }
}