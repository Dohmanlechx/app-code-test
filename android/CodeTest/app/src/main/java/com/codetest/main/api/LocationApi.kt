package com.codetest.main.api

import com.codetest.main.api.models.GetLocationsResponse
import com.codetest.main.api.models.Location
import com.codetest.main.api.models.LocationRequest
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface LocationApi {
    @GET("/locations")
    fun getLocations(
        @Header("X-Api-Key") apiKey: String
    ): Single<GetLocationsResponse>

    @POST("/locations")
    fun postLocation(
        @Header("X-Api-Key") apiKey: String,
        @Body body: LocationRequest
    ): Single<Location>
}