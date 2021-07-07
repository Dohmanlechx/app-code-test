package com.codetest.main.models

data class LocationModel(
    val id: String,
    val name: String,
    val temperature: String,
    val status: WeatherStatus
)

fun LocationModel.weatherInfo(): String =
    temperature + "°C " + String(Character.toChars(status.value))