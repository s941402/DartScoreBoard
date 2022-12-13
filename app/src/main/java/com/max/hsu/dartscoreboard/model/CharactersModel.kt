package com.max.hsu.dartscoreboard.model

import com.max.hsu.dartscoreboard.toolUtil.TOTAL_BLOOD_VOLUME

data class CharactersModel(
    val playerId: Int,
    val currentBlood: Int = TOTAL_BLOOD_VOLUME,
    val isMaster: Boolean,
    val isSelected: Boolean = false
)