package com.codetest.main.usecases

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.component.KoinComponent

abstract class SingleUseCase<T> : KoinComponent {
    protected abstract fun createSingle(): Single<T>

    fun single(): Single<T> =
        createSingle()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}