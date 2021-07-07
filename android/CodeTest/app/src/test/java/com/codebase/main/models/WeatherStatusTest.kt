package com.codebase.main.models

import com.codetest.main.models.WeatherStatus
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class WeatherStatusTest {
    @Test
    fun `WeatherStatus formatted names list`() {
        val formattedNames = WeatherStatus.formattedNames

        val expected: List<String> =
            listOf(
                "Not set",
                "Cloudy",
                "Sunny",
                "Mostly sunny",
                "Partly sunny",
                "Partly sunny rain",
                "Thunder cloud and rain",
                "Tornado",
                "Barely sunny",
                "Lightening",
                "Snow cloud",
                "Rainy"
            )

        assertThat(formattedNames).isEqualTo(expected)
    }
}