package com.codetest.main.api

import com.codetest.main.model.GetLocationsResponse
import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

interface LocationApi {
    @GET
    fun get(@Header("X-Api-Key") apiKey: String, @Url url: String): Observable<JsonObject>

    @GET("/locations")
    fun getLocations(
        @Header("X-Api-Key") apiKey: String
    ): Single<GetLocationsResponse>
}