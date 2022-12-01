package com.max.hsu.dartscoreboard.view

import com.max.hsu.dartscoreboard.base.BaseViewModel
import com.max.hsu.dartscoreboard.model.NumberModel

class MainScoreBoardViewModel : BaseViewModel() {

    private val mapAttackDamage = IntArray(2) { -1 }

    fun getNumberModel() = MutableList(12) {
        NumberModel(it, if (it < 9) it + 1 else 0)
    }

    fun saveAttackDamage(numberModel: NumberModel) {
        val roundup = numberModel.round.roundIndex
        if (roundup in 0 until 2) {
            mapAttackDamage[roundup] = numberModel.realNum
        }
    }
}