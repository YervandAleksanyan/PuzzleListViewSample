package com.example.yervand.puzzlelistviewsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.util.TypedValue
import android.widget.RelativeLayout
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    private var textFirst: TextView? = null
    private var textSecond: TextView? = null
    private var concatBtn: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textFirst = findViewById(R.id.text)
        textSecond = findViewById(R.id.text_2)
//        concatBtn = findViewById(R.id.concat)
//        concatBtn!!.setOnClickListener {
//            concatsTexts()
//        }
    }


    fun concatsTexts() {
        val lineHeight = textFirst!!.paint.textSize
        val displaymetrics = DisplayMetrics()
        val bottomMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, lineHeight, displaymetrics).toInt()
        val params =
            RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
        params.setMargins(0, 0, 0, bottomMargin)
        textSecond!!.layoutParams = params
    }
}
