package com.codetest

import com.codetest.main.api.LocationApi
import com.codetest.main.repositories.LocationRepository
import com.codetest.main.viewmodels.LocationViewModel
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

fun appModules() = listOf(
    mainModule,
    apisModule,
    viewModelsModule,
    reposModules
)

private val mainModule = module {

}

private val apisModule = module {
    single { createOkHttp() }
    single { createCallAdapterFactory() }
    single { createGson() }

    single {
        createRetrofit(client = get(), callFactory = get(), gson = get())
    }

    single { get<Retrofit>().create(LocationApi::class.java) }
}

private val viewModelsModule = module {
    viewModel { LocationViewModel(locationRepo = get()) }
}

private val reposModules = module {
    single { LocationRepository() }
}

private fun createOkHttp(): OkHttpClient =
    OkHttpClient().newBuilder().build()

private fun createCallAdapterFactory(): RxJava2CallAdapterFactory =
    RxJava2CallAdapterFactory.create()

private fun createGson(): GsonConverterFactory =
    GsonConverterFactory.create()

private fun createRetrofit(
    client: OkHttpClient,
    callFactory: RxJava2CallAdapterFactory,
    gson: GsonConverterFactory
): Retrofit =
    Retrofit.Builder()
        .baseUrl("https://app-code-test.kry.pet/")
        .client(client)
        .addCallAdapterFactory(callFactory)
        .addConverterFactory(gson)
        .build()