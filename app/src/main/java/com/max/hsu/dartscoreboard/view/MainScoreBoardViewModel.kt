package com.max.hsu.dartscoreboard.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.max.hsu.dartscoreboard.base.BaseViewModel
import com.max.hsu.dartscoreboard.model.CardModel
import com.max.hsu.dartscoreboard.model.CharactersModel
import com.max.hsu.dartscoreboard.model.NumberModel
import com.max.hsu.dartscoreboard.model.RoundType
import com.max.hsu.dartscoreboard.toolUtil.TOTAL_BLOOD_VOLUME

class MainScoreBoardViewModel : BaseViewModel() {

    private val mapAttackDamage = IntArray(2) { -1 }
    private val topicList = listOf("程式", "公司相關", "科技業相關", "趣味題")
    private val topicQuantityList = listOf(10, 5, 10, 10)

    // 總原始資料
    private var _charactersResult = MutableLiveData<MutableList<CharactersModel>>()
    val charactersResult: LiveData<MutableList<CharactersModel>> = _charactersResult

    // 總原始資料
    private var _cardResult = MutableLiveData<MutableList<CardModel>>()
    val cardResult: LiveData<MutableList<CardModel>> = _cardResult

    // 總原始資料
    private var _numberResult = MutableLiveData<MutableList<NumberModel>>()
    val numberResult: LiveData<MutableList<NumberModel>> = _numberResult


    fun getCharactersModel() {
        _charactersResult.value = MutableList(4) {
            CharactersModel(it, TOTAL_BLOOD_VOLUME, it == 0)
        }
    }

    fun getCardsModel() {
        _cardResult.value = MutableList(4) {
            CardModel(it, topicList[it],topicQuantityList[it])
        }
    }

    fun getNumberModel(round: RoundType = RoundType.ROUND_ONE) {
        _numberResult.value = MutableList(12) {
            NumberModel(it, if (it < 9) it + 1 else 0, round)
        }
    }

    fun saveAttackDamage(roundIndex: Int, score: Int) {
        if (roundIndex in 0 until 2) {
            mapAttackDamage[roundIndex] = score
        }

    }

    fun goRoundTwo() {
        getNumberModel(RoundType.ROUND_TWO)
    }

    fun pickQuestions() {

    }

    fun goAttack() {

    }

}