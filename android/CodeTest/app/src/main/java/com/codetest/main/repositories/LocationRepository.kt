package com.codetest.main.repositories

import androidx.annotation.VisibleForTesting
import com.codetest.main.api.models.LocationRequest
import com.codetest.main.models.LocationModel
import com.codetest.main.usecases.DeleteLocationUseCase
import com.codetest.main.usecases.GetLocationsUseCase
import com.codetest.main.usecases.PostLocationUseCase
import com.codetest.main.util.Prefs
import io.reactivex.Single
import okhttp3.ResponseBody

class LocationRepository(
    private val prefs: Prefs
) {
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var cachedLocations: List<LocationModel> = emptyList()

    fun getLocations(
        singleFromTesting: Single<List<LocationModel>>? = null
    ): Single<List<LocationModel>> =
        (singleFromTesting ?: GetLocationsUseCase(prefs.apiKey()).single())
            .doOnSuccess(::cacheLocations)
            //.onErrorReturn { cachedLocations }

            // ^ This is what I should have used in a real life app, returning latest successfully fetched data if error.
            // But for this specific task where the server is deliberately flaky, we want to know when an error occurs.
            //
            // Feel free to uncomment this and compare the UX. However, this is problematic when the DELETE request fails.

    fun postLocation(location: LocationRequest): Single<LocationModel> =
        PostLocationUseCase(prefs.apiKey(), location).single()

    fun deleteLocation(id: String): Single<ResponseBody> =
        DeleteLocationUseCase(prefs.apiKey(), id).single()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun cacheLocations(locations: List<LocationModel>) {
        cachedLocations = locations
    }
}