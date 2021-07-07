package com.codetest.main.util

import android.content.Context
import android.view.View
import android.widget.Toast
import com.codetest.main.api.models.Location
import com.codetest.main.models.LocationModel
import com.codetest.main.models.WeatherStatus

// Context
fun Context.showToast(msg: String) =
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()

// View
fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

// Model
fun Location.toModel(): LocationModel =
    LocationModel(
        id = id.orEmpty(),
        name = name.orEmpty(),
        temperature = temperature.orEmpty(),
        status = status?.name.toStatus()
    )

fun String?.toStatus(): WeatherStatus =
    WeatherStatus.values().firstOrNull { it.name == this } ?: WeatherStatus.NOT_SET

fun LocationModel.weatherInfo(): String =
    temperature + "°C " + String(Character.toChars(status.value))