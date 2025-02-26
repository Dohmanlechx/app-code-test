package com.codetest.main.ui.activities

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.codetest.CodeTestApplication.Companion.context
import com.codetest.R
import com.codetest.main.util.hide
import com.codetest.main.util.show
import com.uber.autodispose.SingleSubscribeProxy
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDisposable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_base.*

abstract class BaseLceActivity(
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

    private fun hideKeyboard() {
        val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    protected fun setTryAgainOnClickListener(onTryAgain: () -> Unit) {
        btn_try_again.apply {
            show()
            setOnClickListener { onTryAgain() }
        }
    }

    protected fun showContent() {
        layout_loading.hide()
        layout_content.show()
        layout_error.hide()
    }

    protected fun showError() {
        layout_loading.hide()
        layout_content.hide()
        layout_error.show()
    }

    protected fun showErrorDialog(throwable: Throwable) {
        AlertDialog.Builder(this)
            .setTitle(resources.getString(R.string.error_title))
            .setMessage(resources.getString(R.string.error_message, throwable.message))
            .setPositiveButton(resources.getString(R.string.ok)) { _, _ -> }
            .create()
            .show()
    }

    protected fun <T> Single<T>.showLoading(): SingleSubscribeProxy<T> =
        this
            .doOnSubscribe {
                hideKeyboard()
                layout_loading.show()
                layout_content.hide()
                layout_error.hide()
            }
            .subscribeOn(AndroidSchedulers.mainThread())
            .autoDisposable(scope(Lifecycle.Event.ON_DESTROY))
}