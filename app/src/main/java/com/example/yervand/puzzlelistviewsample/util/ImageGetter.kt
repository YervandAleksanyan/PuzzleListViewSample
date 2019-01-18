package com.example.yervand.puzzlelistviewsample.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Html
import com.example.yervand.puzzlelistviewsample.R

class ImageGetter(var context: Context) : Html.ImageGetter {

    override fun getDrawable(source: String): Drawable? {
        val id: Int

        if (source == "stack.jpg") {
            id = R.drawable.stack
        } else {
            return null
        }

        val d = (context).resources.getDrawable(id)
        d.setBounds(0, 0, d.intrinsicWidth, d.intrinsicHeight)
        return d
    }
}