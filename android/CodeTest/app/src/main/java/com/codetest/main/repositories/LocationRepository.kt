package com.codetest.main.repositories

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
    // TODO test, weatherstatus, also maybe ui
    private var cachedLocations: List<LocationModel> = emptyList()

    fun getLocations(): Single<List<LocationModel>> =
        GetLocationsUseCase(prefs.apiKey())
            .single()
            .doOnSuccess { cachedLocations = it }
            //.onErrorReturn { cachedLocations }

            // ^ This is what I should have used in a real life app, returning latest successfully fetched data if error.
            // But for this specific task where the server is deliberately flaky, we want to know when an error occurs.
            //
            // Feel free to uncomment this and compare the UX. However, this is problematic when the DELETE request fails.

    fun postLocation(location: LocationRequest): Single<LocationModel> =
        PostLocationUseCase(prefs.apiKey(), location).single()

    fun deleteLocation(id: String): Single<ResponseBody> =
        DeleteLocationUseCase(prefs.apiKey(), id).single()
}