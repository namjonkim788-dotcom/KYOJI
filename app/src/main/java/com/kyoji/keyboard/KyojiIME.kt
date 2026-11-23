package com.kyoji.keyboard

import android.inputmethodservice.InputMethodService
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import android.graphics.Color

class KyojiIME : InputMethodService() {
    private var persian = true
    private var counter = 0
    private lateinit var keyboard: LinearLayout

    override fun onCreateInputView(): View {
        keyboard = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.rgb(24, 24, 24))
            setPadding(3, 3, 3, 3)
        }
        rebuild()
        return keyboard
    }

    private fun rebuild() {
        keyboard.removeAllViews()
        val toolbar = LinearLayout(this)
        addButton(toolbar, if (persian) "FA" else "EN", 1f) { persian = !persian; rebuild() }
        addButton(toolbar, "⚙", 1f) { Toast.makeText(this, "برای فعال/انتخاب کیبورد از برنامه KYOJI استفاده کن", Toast.LENGTH_SHORT).show() }
        addButton(toolbar, "123", 1f) { insert(if (persian) "۱۲۳" else "123") }
        addButton(toolbar, "${counter}", 1f) { insert(counter.toString()); counter = (counter + 1) % 10 }
        keyboard.addView(toolbar)

        val rows = if (persian) listOf("ضصثقفغعهخ", "شسیبلاتنم", "ظطزرذدپ") else listOf("qwertyuiop", "asdfghjkl", "zxcvbnm")
        rows.forEachIndexed { index, chars ->
            val row = LinearLayout(this)
            chars.forEach { char -> addButton(row, char.toString(), 1f) { insert(char.toString()) } }
            if (index == 2) addButton(row, "⇧", 1.15f) { persian = !persian; rebuild() }
            keyboard.addView(row)
        }

        val bottom = LinearLayout(this)
        addButton(bottom, "،", 1f) { insert(if (persian) "،" else ",") }
        addButton(bottom, "؟", 1f) { insert(if (persian) "؟" else "?") }
        addButton(bottom, "Space", 4f) { insert(" ") }
        addButton(bottom, "⌫", 1.2f) { currentInputConnection?.deleteSurroundingText(1, 0) }
        addButton(bottom, "↵", 1.2f) { enter() }
        keyboard.addView(bottom)
    }

    private fun addButton(row: LinearLayout, label: String, weight: Float, action: () -> Unit) {
        val button = Button(this).apply {
            text = label
            textSize = 14f
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.rgb(55, 55, 55))
            isAllCaps = false
            setPadding(0, 0, 0, 0)
            setOnClickListener { action() }
        }
        row.addView(button, LinearLayout.LayoutParams(0, 48, weight))
    }

    private fun insert(text: String) { currentInputConnection?.commitText(text, 1) }

    private fun enter() {
        val connection = currentInputConnection ?: return
        val action = currentInputEditorInfo?.imeOptions?.and(EditorInfo.IME_MASK_ACTION) ?: EditorInfo.IME_ACTION_NONE
        if (action != EditorInfo.IME_ACTION_NONE && action != EditorInfo.IME_ACTION_UNSPECIFIED && connection.performEditorAction(action)) return
        connection.commitText("\n", 1)
    }
}
