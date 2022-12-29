package com.max.hsu.dartscoreboard.view

import com.max.hsu.dartscoreboard.model.CardModel
import com.max.hsu.dartscoreboard.model.CharactersModel
import com.max.hsu.dartscoreboard.model.NumberModel

interface ScoreBoardCallBack {
    fun numberClick(numberModel : NumberModel)
    fun cardClick(cardModel : CardModel,position: Int)
    fun changeChooseCharacters(charactersModel : CharactersModel, position: Int)
}