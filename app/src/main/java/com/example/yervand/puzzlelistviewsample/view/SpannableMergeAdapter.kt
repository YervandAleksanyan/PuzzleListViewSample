package com.example.yervand.puzzlelistviewsample.view

import android.os.Build
import android.support.v7.widget.RecyclerView
import android.text.*
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.yervand.puzzlelistviewsample.R
import com.example.yervand.puzzlelistviewsample.util.TextSurroundSpan
import com.example.yervand.puzzlelistviewsample.util.convertDpToPixel
import kotlin.math.ceil

class SpannableMergeAdapter(val parentWidth: Int, val dataSet: List<String>) :
    RecyclerView.Adapter<SpannableViewHolder>() {

    enum class ScrollDirection(var value: Int) {
        UP(1),
        DOWN(-1)
    }

    private var lastIndex = -1
    private var leadingSpace = 0
    private var trailingSpace = 0

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): SpannableViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.cell_text, viewGroup, false)
        return SpannableViewHolder(view)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(viewHolder: SpannableViewHolder, position: Int) {

        // properties initial setup
        val textData = dataSet[position]
        val textView = viewHolder.textView
        val direction = if (position > lastIndex) {
            ScrollDirection.DOWN
        } else {
            ScrollDirection.UP
        }

        var tempLayout: StaticLayout? = null
        val marginByDirection = when (direction) {
            ScrollDirection.DOWN -> trailingSpace
            else -> leadingSpace
        }

        val linePositionByDirection = when (direction) {
            ScrollDirection.DOWN -> 1
            else -> -1
        }
        val spannableString =
            Html.fromHtml(
                textData
            ) as SpannableStringBuilder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            when (direction) {
                ScrollDirection.DOWN -> {
                    spannableString.setSpan(
                        TextSurroundSpan(
                            1,
                            (marginByDirection + convertDpToPixel(
                                viewHolder.textView.lineSpacingMultiplier
                            )).toInt()
                        ),
                        0,
                        spannableString.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                else -> {
                    spannableString.setSpan(
                        TextSurroundSpan(
                            1,
                            (marginByDirection + convertDpToPixel(
                                viewHolder.textView.lineSpacingMultiplier
                            )).toInt()
                        ),
                        textView.layout.getLineStart(textView.lineCount-1),
                        spannableString.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }
        }

        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> tempLayout = StaticLayout.Builder
                .obtain(spannableString, 0, spannableString.length, textView.paint, parentWidth)
                .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                .setLineSpacing(textView.lineSpacingExtra, textView.lineSpacingMultiplier)
                .setIncludePad(textView.includeFontPadding)
                .setBreakStrategy(textView.breakStrategy)
                .setHyphenationFrequency(textView.hyphenationFrequency)
                .build()
            else -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                tempLayout = StaticLayout(
                    spannableString,
                    textView.paint,
                    spannableString.length,
                    Layout.Alignment.ALIGN_NORMAL,
                    textView.lineSpacingMultiplier,
                    textView.lineSpacingExtra,
                    textView.includeFontPadding
                )
            }
        }
        val lineCount = tempLayout!!.lineCount

        Log.i(
            "tag",
            "Text - ${textData.substring(0..10)},Position - $position, MarginByDirection - $marginByDirection "
        )
        val leadLineWidth = tempLayout.getLineWidth(0)
        val leadLineLeft = tempLayout.getLineLeft(0)
        val leadLineRight = tempLayout.getLineRight(0)

        val trailLineWidth = tempLayout.getLineWidth(lineCount - 1)
        val trailLineLeft = tempLayout.getLineLeft(lineCount - 1)
        val trailLineRight = tempLayout.getLineRight(lineCount - 1)

        leadingSpace = ceil(tempLayout.getLineWidth(0) - tempLayout.getLineLeft(0)).toInt()
        trailingSpace = ceil(tempLayout.getLineRight(lineCount - 1)).toInt()
        viewHolder.textView.text = spannableString
        lastIndex = position
    }
}