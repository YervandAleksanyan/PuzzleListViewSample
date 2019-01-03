package com.example.yervand.puzzlelistviewsample

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Layout
import android.text.Spannable
import android.text.SpannableString
import android.text.StaticLayout
import android.text.style.LeadingMarginSpan
import android.util.Log
import android.widget.RelativeLayout
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    private var textFirst: TextView? = null
    private var textSecond: TextView? = null
    private var concatBtn: TextView? = null
    private var parent: RelativeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textFirst = findViewById(R.id.text)
        textSecond = findViewById(R.id.text_2)
        concatBtn = findViewById(R.id.concat)
        concatBtn!!.setOnClickListener {
            concatenateTexts()
        }
        parent = findViewById(R.id.parent)
    }


    private fun concatenateTexts() {
        val text = textFirst!!.text
        val layout = textFirst!!.layout
        val paint = layout.paint
        var tempLayout: StaticLayout? = null
        var textLineHeight = paint.textSize

        when {
            SDK_INT >= M -> tempLayout = StaticLayout.Builder
                .obtain(text, 0, text.length, layout.paint, layout.width)
                .setAlignment(layout.alignment)
                .setLineSpacing(textFirst!!.lineSpacingExtra, textFirst!!.lineSpacingMultiplier)
                .setIncludePad(textFirst!!.includeFontPadding)
                .setBreakStrategy(textFirst!!.breakStrategy)
                .setHyphenationFrequency(textFirst!!.hyphenationFrequency)
                .build()
            else -> if (SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                tempLayout = StaticLayout(
                    text,
                    paint,
                    text.length,
                    layout.alignment,
                    textFirst!!.lineSpacingMultiplier,
                    textFirst!!.lineSpacingExtra,
                    textFirst!!.includeFontPadding
                )
            }
        }
        val lineCount = tempLayout!!.lineCount
        val spannableString =
            SpannableString(textSecond!!.text)
        spannableString.setSpan(
            TextSurroundSpan(1, (tempLayout.getLineRight(lineCount - 1) + layout.spacingMultiplier).toInt()),
            0,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textSecond!!.text = spannableString

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Log.i("tag", textFirst!!.lineHeight.toString())
            Log.i("tag", textFirst!!.paint.textSize.toString())
            textSecond!!.y = textSecond!!.y - (textFirst!!.lineHeight)
        }
    }
}

private fun convertPixelsToDp(px: Float): Float {
    val metrics = Resources.getSystem().displayMetrics
    val dp = px / (metrics.densityDpi / 160f)
    return Math.round(dp).toFloat()
}

private fun convertDpToPixel(dp: Float): Float {
    val metrics = Resources.getSystem().displayMetrics
    val px = dp * (metrics.densityDpi / 160f)
    return Math.round(px).toFloat()
}

internal class TextSurroundSpan(private val lines: Int, private val margin: Int) :
    LeadingMarginSpan.LeadingMarginSpan2 {

    override fun getLeadingMargin(first: Boolean): Int {
        return if (first) {
            margin
        } else {
            0
        }
    }

    override fun drawLeadingMargin(
        c: Canvas, p: Paint, x: Int, dir: Int,
        top: Int, baseline: Int, bottom: Int, text: CharSequence,
        start: Int, end: Int, first: Boolean, layout: Layout
    ) {
    }

    override fun getLeadingMarginLineCount(): Int {
        return lines
    }
}
