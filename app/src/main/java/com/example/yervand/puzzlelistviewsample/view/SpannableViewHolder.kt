package com.example.yervand.puzzlelistviewsample.view

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.yervand.puzzlelistviewsample.R

class SpannableViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    var textView = item.findViewById<TextView>(R.id.text)
}