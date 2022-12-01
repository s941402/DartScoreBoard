package com.max.hsu.dartscoreboard.view

import com.max.hsu.dartscoreboard.model.NumberModel

interface ScoreBoardCallBack {
    fun numberClick(numberModel : NumberModel)
}