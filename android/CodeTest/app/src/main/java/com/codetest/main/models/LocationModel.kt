package com.codetest.main.models

data class LocationModel(
    val id: String,
    val name: String,
    val temperature: Int,
    val status: WeatherStatus
)