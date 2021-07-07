package com.codetest.main.usecases

import com.codetest.main.api.LocationApi
import com.codetest.main.api.models.Location
import com.codetest.main.models.LocationModel
import com.codetest.main.models.toStatus
import io.reactivex.Single
import org.koin.core.component.inject

class GetLocationsUseCase(private val apiKey: String) : SingleUseCase<List<LocationModel>>() {
    private val locationApi: LocationApi by inject()

    override fun createSingle(): Single<List<LocationModel>> =
        locationApi
            .getLocations(apiKey)
            .map { response -> response.locations?.map { it.toModel() } ?: emptyList() }

    private fun Location.toModel(): LocationModel =
        LocationModel(
            id = id.orEmpty(),
            name = name.orEmpty(),
            temperature = temperature.orEmpty(),
            status = status?.name.toStatus()
        )
}