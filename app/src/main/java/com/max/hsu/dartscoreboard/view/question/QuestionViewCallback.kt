package com.max.hsu.dartscoreboard.view.question

import com.max.hsu.dartscoreboard.model.QuestionModel

interface QuestionViewCallback {
    fun changeSelectedAnswer(item: QuestionModel, position: Int)
}