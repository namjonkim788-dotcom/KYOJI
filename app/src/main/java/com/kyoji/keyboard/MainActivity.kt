package com.kyoji.keyboard

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(28, 32, 28, 28)
            setBackgroundColor(0xff151515.toInt())
        }
        val title = TextView(this).apply { text = "KYOJI"; textSize = 30f; setTextColor(0xffff4444.toInt()) }
        val subtitle = TextView(this).apply { text = "Persian • English keyboard"; textSize = 16f; setTextColor(-1); setPadding(0, 12, 0, 24) }
        val enable = Button(this).apply { text = "1. Enable KYOJI"; setOnClickListener { startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)) } }
        val select = Button(this).apply { text = "2. Select KYOJI"; setOnClickListener { (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showInputMethodPicker() } }
        val note = TextView(this).apply { text = "Enable KYOJI in keyboard settings, then select it from the keyboard picker. The keyboard works offline."; setTextColor(0xffcccccc.toInt()); setPadding(0, 24, 0, 0) }
        layout.addView(title); layout.addView(subtitle); layout.addView(enable); layout.addView(select); layout.addView(note)
        setContentView(layout)
    }
}
