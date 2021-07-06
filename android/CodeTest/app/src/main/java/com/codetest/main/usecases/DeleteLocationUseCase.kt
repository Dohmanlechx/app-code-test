package com.codetest.main.usecases

import com.codetest.main.KeyUtil
import com.codetest.main.api.LocationApiService
import io.reactivex.Single
import okhttp3.ResponseBody

class DeleteLocationUseCase(
    private val id: String
) : SingleUseCase<ResponseBody>() {
    override fun createSingle(): Single<ResponseBody> =
        LocationApiService
            .getApi()
            .deleteLocation(KeyUtil().getKey(), id)
}