package com.codetest.main.usecases

import com.codetest.main.model.Location
import io.reactivex.Single

class GetLocationsUseCase : SingleUseCase<List<Location>>() {
    override fun createSingle(): Single<List<Location>> {
        return Single.just(emptyList())
    }
}