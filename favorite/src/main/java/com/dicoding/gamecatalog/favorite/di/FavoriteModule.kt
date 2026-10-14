package com.dicoding.gamecatalog.favorite.di

import com.dicoding.gamecatalog.favorite.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val favoriteModule = module {
    viewModel { FavoriteViewModel(get()) }
}

// lazy memastikan favoriteModule hanya dimuat sekali walaupun FavoriteFragment dibuat berulang kali
private val loadFavoriteModule by lazy { loadKoinModules(favoriteModule) }

fun injectFavoriteModule() = loadFavoriteModule
