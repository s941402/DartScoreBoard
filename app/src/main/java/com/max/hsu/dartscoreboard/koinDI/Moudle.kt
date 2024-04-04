package com.max.hsu.dartscoreboard.koinDI

import org.koin.core.context.loadKoinModules

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        listOf(viewModelModule)
    )
}