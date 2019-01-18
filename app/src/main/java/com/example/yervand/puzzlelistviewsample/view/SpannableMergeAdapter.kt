package com.example.yervand.puzzlelistviewsample.view

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.Toast
import com.example.yervand.puzzlelistviewsample.R
import com.example.yervand.puzzlelistviewsample.view.managers.TextLayoutManager
import kotlin.math.abs


class SpannableMergeAdapter(val context: Context, val textLayoutManager: TextLayoutManager) :
    RecyclerView.Adapter<SpannableViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): SpannableViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.cell_text, viewGroup, false)
        return SpannableViewHolder(view)
    }

    override fun getItemCount(): Int = textLayoutManager.items.size

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(viewHolder: SpannableViewHolder, position: Int) {

        val spannableModel = textLayoutManager.getSpannableStringModelForTextEntity(position, viewHolder.textView)
        viewHolder.textView.text =
                spannableModel.spannableString
        viewHolder.textView.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                val touchY = (motionEvent.y).toInt()
                val touchX = motionEvent.x.toInt()
                if (touchY <= abs(spannableModel.topOffset) && touchY >= 0) {
                    if (touchX <= spannableModel.startMargin) {
                        Toast.makeText(
                            context,
                            textLayoutManager.items[position - 1].text.substring(19..20),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            textLayoutManager.items[position].text.substring(19..20),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        context,
                        textLayoutManager.items[position].text.substring(19..20),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            true
        }
    }
}