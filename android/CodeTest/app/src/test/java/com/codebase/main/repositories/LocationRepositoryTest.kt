package com.codebase.main.repositories

import android.content.Context
import com.codetest.main.api.models.Location
import com.codetest.main.api.models.LocationRequest
import com.codetest.main.models.LocationModel
import com.codetest.main.models.WeatherStatus
import com.codetest.main.repositories.LocationRepository
import com.codetest.main.usecases.PostLocationUseCase
import com.codetest.main.util.Prefs
import com.codetest.main.util.toModel
import com.codetest.main.util.toStatus
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Spy

class LocationRepositoryTest {
    @Spy
    private lateinit var spyLocationRepo: LocationRepository

    @Mock
    private lateinit var mockPrefs: Prefs

    private val dummyLocation: LocationModel =
        LocationModel("", "", 0, WeatherStatus.NOT_SET)

    @Before
    fun setup() {
        mockPrefs = mock()
        whenever(mockPrefs.apiKey()).thenReturn("")
        spyLocationRepo = spy(LocationRepository(prefs = mockPrefs))
    }

    @Test
    fun `Successful locations fetch should update the cached list`() {
        assertThat(spyLocationRepo.cachedLocations.size).isEqualTo(0)

        spyLocationRepo
            .getLocations(singleFromTesting = Single.just(listOf(dummyLocation, dummyLocation, dummyLocation)))
            .test()

        verify(spyLocationRepo, times(1)).cacheLocations(any())
        assertThat(spyLocationRepo.cachedLocations.size).isEqualTo(3)
    }

    @Test
    fun `Failed locations fetch should not update the cached list`() {
        assertThat(spyLocationRepo.cachedLocations.size).isEqualTo(0)

        spyLocationRepo
            .getLocations(singleFromTesting = Single.error(Exception()))
            .test()
            .assertFailure(Exception::class.java)

        verify(spyLocationRepo, never()).cacheLocations(any())
        assertThat(spyLocationRepo.cachedLocations.size).isEqualTo(0)
    }

    @Test
    fun `Post location should return LocationModel with corresponding values`() {
        val postLocationRequest =
            LocationRequest(
                name = "Tokyo",
                temperature = 41,
                status = "SUNNY"
            )

        MockPostLocationUseCase(postLocationRequest)
            .createSingle()
            .test()
            .assertValue {
                postLocationRequest.name == it.name
                        && postLocationRequest.temperature == it.temperature
                        && it.status == WeatherStatus.SUNNY
            }
    }
}

private class MockPostLocationUseCase(
    private val locationRequest: LocationRequest
) : PostLocationUseCase(apiKey = "", location = locationRequest) {
    override fun createSingle(): Single<LocationModel> {
        val response =
            locationRequest.let { Location("id", it.name, it.temperature, it.status.toStatus()) }
        return Single.just(response).map { it.toModel() }
    }
}