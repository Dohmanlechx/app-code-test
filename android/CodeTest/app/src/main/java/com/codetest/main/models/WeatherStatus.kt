package com.codetest.main.models

import java.util.*

enum class WeatherStatus(val value: Int) {
    UNKNOWN(-1),
    CLOUDY(0x2601),
    SUNNY(0x2600),
    MOSTLY_SUNNY(0x1F324),
    PARTLY_SUNNY(0x26C5),
    PARTLY_SUNNY_RAIN(0x1F326),
    THUNDER_CLOUD_AND_RAIN(0x26C8),
    TORNADO(0x1F32A),
    BARELY_SUNNY(0x1F325),
    LIGHTENING(0x1F329),
    SNOW_CLOUD(0x1F328),
    RAINY(0x1F327);

    companion object {
        val formattedNames: List<String>
            get() = values()
                .map { it.name }
                .map { it.replace("_", " ") }
                .map { it[0].toUpperCase() + it.substring(1).toLowerCase(Locale.ROOT) }

    }
}

fun String?.toStatus(): WeatherStatus =
    WeatherStatus.values().firstOrNull { it.name == this } ?: WeatherStatus.UNKNOWN