package com.max.hsu.dartscoreboard.koinDI

import com.max.hsu.dartscoreboard.view.MainScoreBoardViewModel
import com.max.hsu.dartscoreboard.view.question.QuestionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { MainScoreBoardViewModel() }
    viewModel { QuestionViewModel(get()) }
}