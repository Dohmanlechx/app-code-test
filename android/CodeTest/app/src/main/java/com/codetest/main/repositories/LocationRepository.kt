package com.codetest.main.repositories

import com.codetest.main.models.LocationModel
import com.codetest.main.api.models.LocationRequest
import com.codetest.main.usecases.GetLocationsUseCase
import com.codetest.main.usecases.PostLocationUseCase
import io.reactivex.Single

class LocationRepository {
    fun postLocation(location: LocationRequest): Single<LocationModel> =
        PostLocationUseCase(location).single()

    fun fetchLocations(): Single<List<LocationModel>> =
        GetLocationsUseCase().single()
}