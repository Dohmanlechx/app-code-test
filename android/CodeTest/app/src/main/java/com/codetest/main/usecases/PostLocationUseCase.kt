package com.codetest.main.usecases

import com.codetest.main.api.LocationApi
import com.codetest.main.api.models.LocationRequest
import com.codetest.main.models.LocationModel
import com.codetest.main.util.toModel
import io.reactivex.Single
import org.koin.core.component.inject

open class PostLocationUseCase(
    private val apiKey: String,
    private val location: LocationRequest
) : SingleUseCase<LocationModel>() {
    private val locationApi: LocationApi by inject()

    override fun createSingle(): Single<LocationModel> =
        locationApi
            .postLocation(apiKey, location)
            .map { it.toModel() }
}