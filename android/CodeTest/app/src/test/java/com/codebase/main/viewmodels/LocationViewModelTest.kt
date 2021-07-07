package com.codebase.main.viewmodels

import com.codetest.main.api.models.LocationRequest
import com.codetest.main.viewmodels.LocationViewModel
import com.nhaarman.mockito_kotlin.mock
import org.junit.Test

class LocationViewModelTest {
    private val viewModel = LocationViewModel(locationRepo = mock())

    @Test
    fun `Unrealistically cold temperature should throw Exception`() {
        viewModel
            .postLocation(
                LocationRequest(
                    name = "Very cold place",
                    temperature = -300,
                    status = "SNOW_CLOUD"
                )
            )
            .test()
            .assertFailure(Exception::class.java)
    }
}