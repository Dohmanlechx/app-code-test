package com.codetest.main.usecases

import com.codetest.main.KeyUtil
import com.codetest.main.api.LocationApiService
import com.codetest.main.models.Location
import com.codetest.main.models.LocationModel
import com.codetest.main.models.LocationRequest
import com.codetest.main.models.toStatus
import io.reactivex.Single

class PostLocationUseCase(
    private val location: LocationRequest
) : SingleUseCase<LocationModel>() {
    override fun createSingle(): Single<LocationModel> =
        LocationApiService
            .getApi()
            .postLocation(KeyUtil().getKey(), location)
            .map { it.toModel() }

    private fun Location.toModel(): LocationModel =
        LocationModel(
            id = id.orEmpty(),
            name = name.orEmpty(),
            temperature = temperature.orEmpty(),
            status = status?.name.toStatus()
        )
}