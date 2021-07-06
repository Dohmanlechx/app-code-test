package com.codetest.main.api

import com.codetest.main.model.GetLocationsResponse
import com.codetest.main.model.Location
import com.google.gson.JsonObject
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

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

    fun get(apiKey: String, url: String, success: (JsonObject) -> Unit, error: (String?) -> Unit) {
        api.get(apiKey, url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    success(it)
                },
                onError = {
                    error(it.message)
                }
            )
    }
}