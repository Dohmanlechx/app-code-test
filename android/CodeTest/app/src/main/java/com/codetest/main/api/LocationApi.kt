package com.codetest.main.api

import com.codetest.main.api.models.GetLocationsResponse
import com.codetest.main.api.models.Location
import com.codetest.main.api.models.LocationRequest
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.*

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

    @DELETE("/locations/{id}")
    fun deleteLocation(
        @Header("X-Api-Key") apiKey: String,
        @Path("id") id: String
    ): Single<ResponseBody>
}