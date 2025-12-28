package com.example.notegen.ui.layouts

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.notegen.R

enum class ToastStatus {
    SUCCESS, ERROR, INFO
}

fun ToastHelper(
    context: Context,
    message: String,
    status: ToastStatus = ToastStatus.INFO
) {

    // 1. Determine Icon and Color based on status
    val (iconRes, iconColorHex) = when (status) {
        ToastStatus.SUCCESS -> Pair(R.drawable.ic_success, "#4CAF50") // Green Check
        ToastStatus.ERROR ->   Pair(R.drawable.ic_error,   "#FF5252") // Red X
        ToastStatus.INFO ->    Pair(android.R.drawable.ic_dialog_info, "#B0B0B0") // Grey Info
    }

    // Root layout (Dark ChatGPT style pill)
    val layout = LinearLayout(context).apply {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        setPadding(32, 20, 32, 20)

        background = GradientDrawable().apply {
            setColor(Color.parseColor("#1E1E1E")) // Dark Grey Background
            cornerRadius = 24f
        }

        elevation = 8f
    }

    // Icon (Now tinted Red, Green, or Grey)
    val iconView = ImageView(context).apply {
        setImageResource(iconRes)

        // CHANGED: Uses the specific color defined above
        setColorFilter(Color.parseColor(iconColorHex))

        layoutParams = LinearLayout.LayoutParams(36, 36).apply {
            marginEnd = 16
        }
    }

    // Text
    val textView = TextView(context).apply {
        text = message
        textSize = 14.5f
        setTextColor(Color.parseColor("#E6E6E6")) // Light Grey Text
        typeface = Typeface.SANS_SERIF
        maxLines = 2
    }

    layout.addView(iconView)
    layout.addView(textView)

    // Show toast at TOP
    Toast(context).apply {
        setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 120)
        duration = Toast.LENGTH_SHORT
        @Suppress("DEPRECATION")
        view = layout
    }.show()
}