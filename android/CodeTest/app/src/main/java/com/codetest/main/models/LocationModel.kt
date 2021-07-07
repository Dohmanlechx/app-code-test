package com.codetest.main.models

data class LocationModel(
    val id: String,
    val name: String,
    val temperature: String,
    val status: WeatherStatus
)