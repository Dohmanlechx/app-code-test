package com.codetest.main.activities

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.codetest.R
import com.codetest.main.extensions.hide
import com.codetest.main.extensions.show
import kotlinx.android.synthetic.main.activity_base.*

abstract class BaseActivity(
    @LayoutRes private val contentView: Int
) : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        inflateContentView()
    }

    private fun inflateContentView() {
        LayoutInflater.from(this).inflate(contentView, layout_content, true)
    }

    protected fun showLoading() {
        layout_loading.show()
        layout_content.hide()
    }

    protected fun showContent() {
        layout_content.show()
        layout_loading.hide()
    }

    protected fun showError(throwable: Throwable) {
        showContent()
        AlertDialog.Builder(this)
            .setTitle(resources.getString(R.string.error_title))
            .setMessage(resources.getString(R.string.error_message, throwable.message))
            .setPositiveButton(resources.getString(R.string.ok)) { _, _ -> }
            .create()
            .show()
    }
}