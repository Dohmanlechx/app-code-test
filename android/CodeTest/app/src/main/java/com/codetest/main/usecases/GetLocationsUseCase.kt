package com.codetest.main.usecases

import com.codetest.main.KeyUtil
import com.codetest.main.api.LocationApiService
import com.codetest.main.model.Location
import com.codetest.main.model.LocationModel
import com.codetest.main.model.toStatus
import io.reactivex.Single

class GetLocationsUseCase : SingleUseCase<List<LocationModel>>() {
    override fun createSingle(): Single<List<LocationModel>> =
        LocationApiService
            .getApi()
            .getLocations(KeyUtil().getKey())
            .map { response -> response.locations.map { it.toModel() } }

    private fun Location.toModel(): LocationModel =
        LocationModel(
            id = id.orEmpty(),
            name = name.orEmpty(),
            temperature = temperature.orEmpty(),
            status = status?.name.toStatus()
        )
}