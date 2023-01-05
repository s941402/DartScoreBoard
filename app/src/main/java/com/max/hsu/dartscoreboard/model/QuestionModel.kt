package com.max.hsu.dartscoreboard.model

import com.google.gson.annotations.SerializedName
import com.max.hsu.dartscoreboard.R

data class TopicQuestionModel(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("selectQuestion") val selectQuestion: MutableSet<String> = mutableSetOf(),
    @SerializedName("answer") val answer: Int,
    @SerializedName("ability") val ability: Int
)

data class QuestionModel(
    val answerText: String,
    var isSelected: Boolean = false
)

enum class AbilityType(
    val id: Int,
    val abilityName: String,
    val multiple: Int,
    val drawableResId: Int
) {
    Nothing(-1, "單人攻擊", 1, R.drawable.ic_sword),
    FullAttack(0, "全體攻擊", 1, R.drawable.ic_sword),
    Double(1, "單人兩倍攻擊", 2, R.drawable.ic_double_sword),
    Triple(2, "單人三倍攻擊", 3, R.drawable.ic_sword_three),
    Treatment(3, "治療", 1, R.drawable.ic_heart);

    companion object {
        fun fromType(type: Int): AbilityType =
            values().find { it.id == type } ?: Nothing

        fun getMultiple(type: Int) = values().find { it.id == type }?.multiple ?: 1
    }
}
