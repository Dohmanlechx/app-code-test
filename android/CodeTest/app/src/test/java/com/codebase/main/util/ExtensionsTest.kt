package com.codebase.main.util

import com.codetest.main.api.models.Location
import com.codetest.main.models.LocationModel
import com.codetest.main.models.WeatherStatus
import com.codetest.main.util.toModel
import com.google.common.truth.Truth
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

        Truth.assertThat(uiModel.id).isEqualTo("123")
        Truth.assertThat(uiModel.name).isEqualTo("")
        Truth.assertThat(uiModel.temperature).isEqualTo("10")
        Truth.assertThat(uiModel.status).isEqualTo(WeatherStatus.NOT_SET)
    }
}