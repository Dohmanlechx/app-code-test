package com.codetest.main.koin

import com.codetest.main.api.LocationApi
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val apisModule = module {
    single { createOkHttp() }
    single { createCallAdapterFactory() }
    single { createGson() }
    single { createRetrofit(client = get(), callFactory = get(), gson = get()) }

    single { get<Retrofit>().create(LocationApi::class.java) }
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