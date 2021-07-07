package com.codetest.main.api.models

import com.codetest.main.models.WeatherStatus

data class Location(
    val id: String? = null,
    val name: String? = null,
    val temperature: Int? = null,
    val status: WeatherStatus? = null
) {
    companion object {
        const val ERROR_TEMP = -9999
    }
}