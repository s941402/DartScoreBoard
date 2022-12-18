package com.max.hsu.dartscoreboard.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.max.hsu.dartscoreboard.base.BaseViewModel
import com.max.hsu.dartscoreboard.model.*
import com.max.hsu.dartscoreboard.model.AbilityType.Companion.getMultiple
import com.max.hsu.dartscoreboard.toolUtil.TOTAL_BLOOD_VOLUME

class MainScoreBoardViewModel : BaseViewModel() {

    private val mapAttackDamage = IntArray(2) { -1 }

    // 總原始資料
    private var _charactersResult = MutableLiveData<MutableList<CharactersModel>>()
    val charactersResult: LiveData<MutableList<CharactersModel>> = _charactersResult

    // 總原始資料
    private var _cardResult = MutableLiveData<MutableList<CardModel>>()
    val cardResult: LiveData<MutableList<CardModel>> = _cardResult

    // 總原始資料
    private var _numberResult = MutableLiveData<MutableList<NumberModel>>()
    val numberResult: LiveData<MutableList<NumberModel>> = _numberResult

    private var _totalAttackResult = MutableLiveData<Int>()
    val totalAttackResult: LiveData<Int> = _totalAttackResult

    private var _gameOverResult = MutableLiveData<Int>()
    val gameOverResult: LiveData<Int> = _gameOverResult

    private var ability: Int = AbilityType.Nothing.id


    fun getInitCharactersModel() {
        _charactersResult.value = getCharactersModel()
    }

    private fun getCharactersModel() =
        MutableList(4) {
            CharactersModel(it, TOTAL_BLOOD_VOLUME, it == 0)
        }


    fun getInitCardsModel() {
        _cardResult.value = getCardModel()
    }

    private fun getCardModel() =
        MutableList(4) { index ->
            CardModel(index, CardTopic.values().getOrNull(index) ?: CardTopic.Program)
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

    fun updateCardsModel(questionId: Int, position: Int, abilityType: Int) {
        val cardResult = _cardResult.value ?: getCardModel()
        if (position in 0 until cardResult.size) {
            cardResult.getOrNull(position)?.answerList?.add(questionId)
            _cardResult.value = cardResult
        }
        ability = abilityType
        calculateAttack()
    }

    fun calculateAttack() {
        val multiple = getMultiple(ability)
        _totalAttackResult.value = mapAttackDamage.sum() * multiple
    }

    fun attack() {
        val damage = _totalAttackResult.value ?: 0
        val charactersData = _charactersResult.value ?: getCharactersModel()
        var masterPosition = 0
        charactersData.forEachIndexed { index, it ->
            if (!it.isMaster) {
                it.currentBlood -= damage
                it.isDeath = it.currentBlood <= 0
            } else {
                masterPosition = index
                it.isMaster = false
            }
        }
        if (checkIsGameOver(charactersData, masterPosition)) {
            _charactersResult.value = charactersData
            _gameOverResult.value = masterPosition
        } else {
            _charactersResult.value = charactersData
        }
    }

    private fun checkIsGameOver(
        charactersList: MutableList<CharactersModel>,
        masterPosition: Int
    ): Boolean {
        var nextPosition = masterPosition + 1
        while (nextPosition < charactersList.size) {
            charactersList.getOrNull(nextPosition)?.let {
                if (!it.isDeath) {
                    it.isMaster = true
                    return false
                }
            }
            nextPosition++
        }
        nextPosition = 0
        while (nextPosition < masterPosition) {
            charactersList.getOrNull(nextPosition)?.let {
                if (!it.isDeath) {
                    it.isMaster = true
                    return false
                }
            }
            nextPosition++
        }
        return true
    }

    fun cleanResult() {
        mapAttackDamage[0] = -1
        mapAttackDamage[1] = -1
        getNumberModel()
    }

}