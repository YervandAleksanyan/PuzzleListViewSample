package com.example.yervand.puzzlelistviewsample.view.managers

import android.text.SpannableStringBuilder

data class SpannableStringModel(
    var spannableString: SpannableStringBuilder,
    var startMargin: Int,
    var topOffset: Int
)