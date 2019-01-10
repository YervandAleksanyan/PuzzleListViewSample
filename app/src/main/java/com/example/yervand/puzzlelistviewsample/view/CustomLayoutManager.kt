package com.example.yervand.puzzlelistviewsample.view

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import com.example.yervand.puzzlelistviewsample.R


class CustomLayoutManager(var context: Context) : LinearLayoutManager(context) {

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
        for (i in 0 until state!!.itemCount - 1) {
            val itemView = recycler!!.getViewForPosition(i).findViewById<TextView>(R.id.text)
            itemView.text = "${itemView.text}$i"
//            recycler.bindViewToPosition(itemView, i)
        }
    }
}