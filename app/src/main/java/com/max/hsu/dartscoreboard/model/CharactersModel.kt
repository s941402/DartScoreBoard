package com.max.hsu.dartscoreboard.model

import androidx.annotation.DrawableRes
import com.max.hsu.dartscoreboard.R
import com.max.hsu.dartscoreboard.toolUtil.TOTAL_BLOOD_VOLUME

data class CharactersModel(
    val playerId: Int,
    @DrawableRes val iconHeadDrawable: Int,
    @DrawableRes val iconBodyDrawable: Int,
    var currentBlood: Int = TOTAL_BLOOD_VOLUME,
    var isMaster: Boolean,
    var isDeath: Boolean = false,
    var isSelected: Boolean = false,
    var canSelect: Boolean = false
)

enum class CharactersPlayer(
    val id: Int,
    val iconHeadDrawable: Int,
    val iconBodyDrawable: Int,
    val currentBlood: Int
) {
    ONE(0, R.drawable.ic_chick_head_body, R.drawable.ic_chick_full_body, TOTAL_BLOOD_VOLUME),
    TWO(1, R.drawable.ic_tim_head_body, R.drawable.ic_tim_full_body, TOTAL_BLOOD_VOLUME),
    THREE(2, R.drawable.ic_bird_head_body, R.drawable.ic_bird_full_body, TOTAL_BLOOD_VOLUME),
    FOUR(3, R.drawable.ic_bear_head_body, R.drawable.ic_bear_full_body, TOTAL_BLOOD_VOLUME),
}