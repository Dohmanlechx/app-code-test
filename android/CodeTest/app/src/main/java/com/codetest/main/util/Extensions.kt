package com.codetest.main.util

import android.content.Context
import android.view.View
import android.widget.Toast
import com.codetest.main.api.models.Location
import com.codetest.main.api.models.Location.Companion.ERROR_TEMP
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
        temperature = temperature ?: ERROR_TEMP,
        status = status?.name.toStatus()
    )

fun String?.toStatus(): WeatherStatus =
    WeatherStatus.values().firstOrNull { it.name == this?.uppercase() } ?: WeatherStatus.NOT_SET

fun LocationModel.weatherInfo(): String {
    val temp = if (temperature == ERROR_TEMP) "" else "$temperature°C "
    val weather = if (status == WeatherStatus.NOT_SET) "" else String(Character.toChars(status.value))
    return temp + weather
}