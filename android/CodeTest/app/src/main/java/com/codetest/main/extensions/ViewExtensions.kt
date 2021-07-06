package com.codetest.main.extensions

import android.content.Context
import android.view.View
import android.widget.Toast

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun Context.showToast(msg: String) =
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
