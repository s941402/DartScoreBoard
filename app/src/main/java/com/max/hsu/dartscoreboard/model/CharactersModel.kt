package com.max.hsu.dartscoreboard.model

import com.max.hsu.dartscoreboard.toolUtil.TOTAL_BLOOD_VOLUME

data class CharactersModel(
    val playerId: Int,
    var currentBlood: Int = TOTAL_BLOOD_VOLUME,
    var isMaster: Boolean,
    var isDeath: Boolean = false,
    var isSelected: Boolean = false,
    var canSelect: Boolean = false
)