package com.codetest.main.repositories

import com.codetest.main.api.models.LocationRequest
import com.codetest.main.models.LocationModel
import com.codetest.main.usecases.DeleteLocationUseCase
import com.codetest.main.usecases.GetLocationsUseCase
import com.codetest.main.usecases.PostLocationUseCase
import com.codetest.main.util.KeyUtil
import io.reactivex.Single
import okhttp3.ResponseBody

class LocationRepository(
    private val keyUtil: KeyUtil
) {
    fun getLocations(): Single<List<LocationModel>> =
        GetLocationsUseCase(keyUtil.getKey()).single()

    fun postLocation(location: LocationRequest): Single<LocationModel> =
        PostLocationUseCase(keyUtil.getKey(), location).single()

    fun deleteLocation(id: String): Single<ResponseBody> =
        DeleteLocationUseCase(keyUtil.getKey(), id).single()
}