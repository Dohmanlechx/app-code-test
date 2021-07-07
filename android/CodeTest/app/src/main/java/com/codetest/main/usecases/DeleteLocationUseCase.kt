package com.codetest.main.usecases

import com.codetest.main.api.LocationApi
import io.reactivex.Single
import okhttp3.ResponseBody
import org.koin.core.component.inject

class DeleteLocationUseCase(
    private val apiKey: String,
    private val id: String
) : SingleUseCase<ResponseBody>() {
    private val locationApi: LocationApi by inject()

    override fun createSingle(): Single<ResponseBody> =
        locationApi
            .deleteLocation(apiKey, id)
}