package com.example.yervand.puzzlelistviewsample

import android.content.Context
import android.os.Build
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.*
import android.widget.LinearLayout
import android.widget.TextView


class CustomLayoutManager(context: Context) : LinearLayoutManager(context) {


    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        if (itemCount == 0) {
            detachAndScrapAttachedViews(recycler!!)
            return
        }
        detachAndScrapAttachedViews(recycler!!)
        var i = 0
        val cnt = itemCount
        (0 until cnt - 1).forEach { index ->
            val currentChild = (recycler.getViewForPosition(index)) as TextView
            val nextChild = (recycler.getViewForPosition(index + 1)) as TextView

            val text = currentChild.text
            val paint = currentChild.paint
            var tempLayout: StaticLayout? = null
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> tempLayout = StaticLayout.Builder
                    .obtain(text, 0, text.length, paint, currentChild.width)
                    .setAlignment(Layout.Alignment.ALIGN_NORMAL)
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
                        Layout.Alignment.ALIGN_NORMAL,
                        currentChild.lineSpacingMultiplier,
                        currentChild.lineSpacingExtra,
                        currentChild.includeFontPadding
                    )
                }
            }
            val lineCount = tempLayout!!.lineCount
            val spannableString =
                Html.fromHtml(nextChild.text.toString()) as SpannableStringBuilder
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                spannableString.setSpan(
                    TextSurroundSpan(
                        1,
                        (tempLayout.getLineRight(lineCount - 1) + convertDpToPixel(currentChild.lineSpacingMultiplier)).toInt()
                    ),
                    0,
                    spannableString.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            nextChild.text = spannableString
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    topMargin = -currentChild.lineHeight
                }
            }
            nextChild.layoutParams = params
            i++
        }
        super.onLayoutChildren(recycler, state)
    }
}