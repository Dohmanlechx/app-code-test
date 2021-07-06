package com.codetest.main.api

import com.codetest.main.api.models.GetLocationsResponse
import com.codetest.main.api.models.Location
import com.codetest.main.api.models.LocationRequest
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

// TODO: Koinize
class LocationApiService {
    private val api: LocationApi

    companion object {
        private val instance = LocationApiService()
        fun getApi(): LocationApiService =
            instance
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://app-code-test.kry.pet/")
            .client(OkHttpClient().newBuilder().build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(LocationApi::class.java)
    }

    fun getLocations(apiKey: String): Single<GetLocationsResponse> {
        return api.getLocations(apiKey)
    }

    fun postLocation(apiKey: String, location: LocationRequest): Single<Location> {
        return api.postLocation(apiKey, location)
    }

    fun deleteLocation(apiKey: String, id: String): Single<ResponseBody> {
        return api.deleteLocation(apiKey, id)
    }
}