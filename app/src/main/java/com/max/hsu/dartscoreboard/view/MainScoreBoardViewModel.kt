package com.max.hsu.dartscoreboard.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.max.hsu.dartscoreboard.base.BaseViewModel
import com.max.hsu.dartscoreboard.model.*
import com.max.hsu.dartscoreboard.model.AbilityType.Companion.getMultiple
import com.max.hsu.dartscoreboard.toolUtil.Player
import com.max.hsu.dartscoreboard.toolUtil.TOTAL_BLOOD_VOLUME

class MainScoreBoardViewModel : BaseViewModel() {

    private val mapAttackDamage = IntArray(2) { -1 }

    // 總原始資料
    private var _charactersResult = MutableLiveData<MutableList<CharactersModel>>()
    val charactersResult: LiveData<MutableList<CharactersModel>> = _charactersResult

    private var _charactersStatusResult = MutableLiveData<Boolean>()
    val charactersStatusResult: LiveData<Boolean> = _charactersStatusResult

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

    private var ability: Int? = null
    private var charactersChoosePosition = -1

    fun getInitCharactersModel() {
        _charactersResult.value = getCharactersModel()
    }

    private fun getCharactersModel() =
        MutableList(CharactersPlayer.values().size) { index ->
            val players = CharactersPlayer.values().getOrNull(index) ?: CharactersPlayer.ONE
            CharactersModel(
                players.id,
                players.iconHeadDrawable,
                players.iconBodyDrawable,
                players.currentBlood,
                index == 0
            )
        }


    fun getInitCardsModel() {
        _cardResult.value = getCardModel()
    }

    private fun getCardModel() =
        MutableList(CardTopic.values().size) { index ->
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
        updateCharactersStatus(abilityType)
        calculateAttack()
    }

    private fun updateCharactersStatus(abilityType: Int) {
        val charactersData = _charactersResult.value ?: getCharactersModel()
        val needSelected =
            abilityType == AbilityType.Double.id || abilityType == AbilityType.Triple.id || abilityType == AbilityType.Nothing.id
        Log.i("TAG", "updateCharactersStatus needSelected: $needSelected")
        if (needSelected) charactersData.forEach { it.canSelect = !it.isMaster }
        Log.i("TAG", "updateCharactersStatus charactersData: $charactersData")
        _charactersStatusResult.value = needSelected
    }

    fun getCharactersChoosePosition() = charactersChoosePosition

    fun updatePreChooseCharacters() {
        val charactersData = _charactersResult.value ?: getCharactersModel()
        if (charactersChoosePosition in 0 until charactersData.size) {
            charactersData.getOrNull(charactersChoosePosition)?.isSelected = false
        }
    }

    // 更新次數方案新選擇的改為true並記錄新的位置
    fun updateCurrentChooseCharacters(charactersPosition: Int) {
        val charactersData = _charactersResult.value ?: getCharactersModel()
        if (charactersPosition in 0 until charactersData.size) {
            charactersData.getOrNull(charactersPosition)?.apply {
                isSelected = true
                charactersChoosePosition = charactersPosition
            }
        }
    }

    fun calculateAttack() {
        ability?.let {
            val multiple = getMultiple(it)
            _totalAttackResult.value = mapAttackDamage.sum() * multiple
        }
    }

    fun checkCanAttack(): String {
        val needSelected =
            ability == AbilityType.Double.id || ability == AbilityType.Triple.id || ability == AbilityType.Nothing.id
        return when {
            ability == null -> "尚未抽題目"
            needSelected && charactersResult.value?.find { it.isSelected } == null -> "尚未選擇攻擊對象"
            mapAttackDamage[0] < 0 -> "尚未輸入第一回合分數"
            mapAttackDamage[1] < 0 -> "尚未輸入第二回合分數"
            else -> ""
        }
    }

    fun attack() {
        val damage = _totalAttackResult.value ?: 0
        val charactersData = _charactersResult.value ?: getCharactersModel()
        val masterPosition = charactersData.indexOfFirst { it.isMaster }
        when (ability) {
            AbilityType.FullAttack.id -> {
                attackAll(charactersData, damage)
            }
            AbilityType.Treatment.id -> {
                treatment(charactersData, damage)
            }
            else -> {
                attackOne(charactersData, damage)
            }
        }
        if (isGameOver(charactersData)) {
            _charactersResult.value = charactersData
            _gameOverResult.value = masterPosition
        } else {
            masterRoundChange(charactersData, masterPosition)
            cleanSelected(charactersData)
            _charactersResult.value = charactersData
        }
    }

    private fun attackOne(charactersData: MutableList<CharactersModel>, damage: Int) {
        charactersData.forEach {
            if (it.isSelected) {
                it.currentBlood -= damage
                it.isDeath = it.currentBlood <= 0
            }
        }
    }

    private fun attackAll(
        charactersData: MutableList<CharactersModel>,
        damage: Int
    ) {
        charactersData.forEach {
            if (!it.isMaster) {
                it.currentBlood -= damage
                it.isDeath = it.currentBlood <= 0
            }
        }
    }

    private fun treatment(
        charactersData: MutableList<CharactersModel>,
        damage: Int
    ) {
        charactersData.forEach {
            if (it.isMaster) {
                val blood = it.currentBlood.plus(damage)
                it.currentBlood = if (blood > TOTAL_BLOOD_VOLUME) TOTAL_BLOOD_VOLUME else blood
            }
        }
    }

    private fun isGameOver(charactersList: MutableList<CharactersModel>): Boolean {
        return charactersList.count { it.isDeath || it.currentBlood <= 0 } >= Player - 1
    }

    private fun masterRoundChange(
        charactersList: MutableList<CharactersModel>,
        masterPosition: Int
    ) {
        var nextPosition = masterPosition + 1
        charactersList.getOrNull(masterPosition)?.let { it.isMaster = false }
        while (nextPosition < charactersList.size) {
            charactersList.getOrNull(nextPosition)?.let {
                if (!it.isDeath) {
                    it.isMaster = true
                    return
                }
            }
            nextPosition++
        }
        nextPosition = 0
        while (nextPosition < masterPosition) {
            charactersList.getOrNull(nextPosition)?.let {
                if (!it.isDeath) {
                    it.isMaster = true
                    return
                }
            }
            nextPosition++
        }
    }

    fun statisticsScoreWinner(): Int {
        val charactersItems = _charactersResult.value ?: getCharactersModel()
        var maxBlood = Int.MIN_VALUE
        var count = 0
        var charactersIndex = 0
        charactersItems.forEachIndexed { index, charactersModel ->
            if (maxBlood < charactersModel.currentBlood) {
                maxBlood = charactersModel.currentBlood
                count = 1
                charactersIndex = index
            } else if (maxBlood == charactersModel.currentBlood) {
                count++
            }
        }
        Log.i("TAG", "statisticsScoreWinner: $count")
        Log.i("TAG", "statisticsScoreWinner: $maxBlood")
        return if (count > 1) -1 else charactersIndex
    }

    private fun cleanSelected(charactersList: MutableList<CharactersModel>) {
        charactersList.forEach {
            it.canSelect = false
            it.isSelected = false
        }
    }

    fun cleanResult() {
        mapAttackDamage[0] = -1
        mapAttackDamage[1] = -1
        charactersChoosePosition = -1
        ability = null
        getNumberModel()
    }

}