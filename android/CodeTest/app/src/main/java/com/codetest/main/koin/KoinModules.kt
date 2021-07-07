package com.codetest.main.koin

import com.codetest.main.repositories.LocationRepository
import com.codetest.main.viewmodels.LocationViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun appModules() = listOf(
    mainModule,
    apisModule,
    viewModelsModule,
    reposModule
)

private val mainModule = module {

}

private val viewModelsModule = module {
    viewModel { LocationViewModel(locationRepo = get()) }
}

private val reposModule = module {
    single { LocationRepository() }
}