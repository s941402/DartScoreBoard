package com.max.hsu.dartscoreboard.model

data class NumberModel(
    val index: Int = 0,
    val realNum: Int = 1,
    var round: RoundType = RoundType.ROUND_ONE
) {
    val displayText: String = when (index) {
        9 -> "返回"
        10 -> "0"
        11 -> round.text
        else -> realNum.toString()
    }
}

enum class RoundType(val text: String,val roundIndex: Int) {
    ROUND_ONE("確認", 0), ROUND_TWO("攻擊", 1)
}