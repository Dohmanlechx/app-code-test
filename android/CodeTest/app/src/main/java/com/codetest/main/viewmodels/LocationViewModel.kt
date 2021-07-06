package com.codetest.main.viewmodels

import androidx.lifecycle.ViewModel
import com.codetest.main.api.models.LocationRequest
import com.codetest.main.models.LocationModel
import com.codetest.main.repositories.LocationRepository
import io.reactivex.Single
import okhttp3.ResponseBody

class LocationViewModel(
    private val locationRepo: LocationRepository
) : ViewModel() {
    fun getLocations(): Single<List<LocationModel>> =
        locationRepo.getLocations()

    fun postLocation(location: LocationRequest): Single<LocationModel> {
        if (location.temperature.toInt() < -273) {
            return Single.error(Exception("It can't be this cold :-)"))
        }

        return locationRepo.postLocation(location)
    }

    fun deleteLocation(id: String): Single<ResponseBody> =
        locationRepo.deleteLocation(id)
}