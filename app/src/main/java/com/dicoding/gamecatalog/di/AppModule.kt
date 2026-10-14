package com.dicoding.gamecatalog.di

import com.dicoding.gamecatalog.core.domain.usecase.GameInteractor
import com.dicoding.gamecatalog.core.domain.usecase.GameUseCase
import com.dicoding.gamecatalog.detail.DetailGameViewModel
import com.dicoding.gamecatalog.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<GameUseCase> { GameInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailGameViewModel(get()) }
}
