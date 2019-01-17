package com.example.yervand.puzzlelistviewsample

import android.os.Build
import android.text.*
import android.widget.TextView
import com.example.yervand.puzzlelistviewsample.util.TextSurroundSpan
import com.example.yervand.puzzlelistviewsample.util.convertDpToPixel
import kotlin.math.ceil

class TextLayoutManager(val width: Int, val manager: TextEntityManager) {
    private val cachedMeasurements = HashMap<Int, SpannableStringBuilder>()
    fun getSpannableStringForTextEntity(textEntityId: Int, textView: TextView): SpannableStringBuilder {
        val margin = cachedMeasurements[textEntityId]
        margin?.let { return it }
        calculateParagraphMeasurements(textEntityId, textView)
        return cachedMeasurements[textEntityId]!!
    }

    private var startMargin = 0

    private fun calculateParagraphMeasurements(textEntityId: Int, textView: TextView) {
        val paragraph = manager.getTextEntitiesInSameParagraph(textEntityId)
        val entity = paragraph.find { it.id == textEntityId }
        val text = entity!!.text
        val isParagraphStart = entity.isParagraphStart

        if (isParagraphStart) {
            startMargin = 0
        }


        var tempLayout: StaticLayout? = null

        val spannableString =
            Html.fromHtml(
                text
            ) as SpannableStringBuilder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            spannableString.setSpan(
                TextSurroundSpan(
                    1,
                    (startMargin + convertDpToPixel(
                        textView.lineSpacingMultiplier
                    )).toInt()
                ),
                0,
                spannableString.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> tempLayout = StaticLayout.Builder
                .obtain(spannableString, 0, spannableString.length, textView.paint, width)
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
        startMargin = ceil(tempLayout.getLineRight(lineCount - 1)).toInt()
        cachedMeasurements[textEntityId] = spannableString
    }
}
