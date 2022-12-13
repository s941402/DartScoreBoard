package com.max.hsu.dartscoreboard.model

data class CardModel(
    val id: Int,
    val typeText: String,
    val quantity: Int,
    val isSelected: Boolean = false
)