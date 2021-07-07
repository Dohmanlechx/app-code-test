package com.codebase.main.util

import com.codetest.main.api.models.Location
import com.codetest.main.models.LocationModel
import com.codetest.main.models.WeatherStatus
import com.codetest.main.util.toModel
import com.codetest.main.util.toStatus
import com.codetest.main.util.weatherInfo
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ExtensionsTest {
    @Test
    fun `Location API Model to UI Model mapping`() {
        val apiModel =
            Location(
                id = "123",
                name = null,
                temperature = "10",
                status = null
            )

        val uiModel: LocationModel = apiModel.toModel()

        assertThat(uiModel.id).isEqualTo("123")
        assertThat(uiModel.name).isEqualTo("")
        assertThat(uiModel.temperature).isEqualTo("10")
        assertThat(uiModel.status).isEqualTo(WeatherStatus.NOT_SET)
    }

    @Test
    fun `CLOUDY (String) to status should be CLOUDY (enum)`() {
        val str = "CLOUDY"
        val enum = str.toStatus()
        assertThat(enum).isEqualTo(WeatherStatus.CLOUDY)
    }

    @Test
    fun `tornado (String) to status should be TORNADO (enum)`() {
        val str = "tornado"
        val enum = str.toStatus()
        assertThat(enum).isEqualTo(WeatherStatus.TORNADO)
    }

    @Test
    fun `Invalid String to status should be NOT_SET (enum)`() {
        val str = "The cake is a lie"
        val enum = str.toStatus()
        assertThat(enum).isEqualTo(WeatherStatus.NOT_SET)
    }

    @Test
    fun `LocationModel to weatherInfo (String) emoji mapping`() {
        val location =
            LocationModel(
                id = "123",
                name = "Stockholm",
                temperature = "24",
                status = WeatherStatus.LIGHTENING
            )

        val weatherInfo = location.weatherInfo()

        assertThat(weatherInfo).isEqualTo("24°C \uD83C\uDF29")
    }
}