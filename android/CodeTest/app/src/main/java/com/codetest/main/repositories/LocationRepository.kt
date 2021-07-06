package com.codetest.main.repositories

import com.codetest.main.api.models.LocationRequest
import com.codetest.main.models.LocationModel
import com.codetest.main.usecases.DeleteLocationUseCase
import com.codetest.main.usecases.GetLocationsUseCase
import com.codetest.main.usecases.PostLocationUseCase
import io.reactivex.Single
import okhttp3.ResponseBody

class LocationRepository {
    fun getLocations(): Single<List<LocationModel>> =
        GetLocationsUseCase().single()

    fun postLocation(location: LocationRequest): Single<LocationModel> {
        if (location.temperature.toInt() < -273) {
            return Single.error(Exception("It can't be this cold :-)"))
        }

        return PostLocationUseCase(location).single()
    }

    fun deleteLocation(id: String): Single<ResponseBody> =
        DeleteLocationUseCase(id).single()
}