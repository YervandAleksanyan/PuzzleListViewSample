package com.example.yervand.puzzlelistviewsample.view

import android.graphics.Canvas
import android.graphics.Rect
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.StaticLayout
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.yervand.puzzlelistviewsample.R
import com.example.yervand.puzzlelistviewsample.util.TextSurroundSpan
import com.example.yervand.puzzlelistviewsample.util.convertDpToPixel

class CustomItemDecoration(var dataSet: List<String>) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        Log.i("tag", "getItemOffsets")
        with(outRect) {
            if (parent.getChildAdapterPosition(view) != 0) {
                top = -(view.findViewById<TextView>(R.id.text)).lineHeight
            }
        }
    }

    var index = 0

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
//        super.onDraw(c, parent, state)
        Log.i("tag", "onDrawOver")
        for (i in 0 until parent.childCount - 1) {
            val currentChild = (parent.getChildAt(i)).findViewById<TextView>(R.id.text)
            val nextChild = (parent.getChildAt(i + 1)).findViewById<TextView>(R.id.text)
            val text = currentChild.text
            val layout = nextChild.layout
            val paint = layout.paint
            var tempLayout: StaticLayout? = null
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> tempLayout = StaticLayout.Builder
                    .obtain(text, 0, text.length, layout.paint, layout.width)
                    .setAlignment(layout.alignment)
                    .setLineSpacing(currentChild.lineSpacingExtra, currentChild.lineSpacingMultiplier)
                    .setIncludePad(currentChild.includeFontPadding)
                    .setBreakStrategy(currentChild.breakStrategy)
                    .setHyphenationFrequency(currentChild.hyphenationFrequency)
                    .build()
                else -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    tempLayout = StaticLayout(
                        text,
                        paint,
                        text.length,
                        layout.alignment,
                        currentChild.lineSpacingMultiplier,
                        currentChild.lineSpacingExtra,
                        currentChild.includeFontPadding
                    )
                }
            }
            val lineCount = tempLayout!!.lineCount
            val spannableString =
                Html.fromHtml(dataSet[i + 1]) as SpannableStringBuilder
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                spannableString.setSpan(
                    TextSurroundSpan(
                        1,
                        (tempLayout.getLineRight(lineCount - 1) + convertDpToPixel(
                            currentChild.lineSpacingMultiplier
                        )).toInt()
                    ),
                    0,
                    spannableString.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            nextChild.text = spannableString
        }
    }
}
