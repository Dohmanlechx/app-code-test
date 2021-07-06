package com.codetest.main.api.models

import com.codetest.main.models.WeatherStatus

data class Location(
    val id: String? = null,
    val name: String? = null,
    val temperature: String? = null,
    val status: WeatherStatus? = null
)