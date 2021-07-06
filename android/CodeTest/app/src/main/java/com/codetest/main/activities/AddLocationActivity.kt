package com.codetest.main.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.codetest.R

class AddLocationActivity : AppCompatActivity() {
    companion object {
        fun intent(context: Context): Intent =
            Intent(context, AddLocationActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_location)
    }
}