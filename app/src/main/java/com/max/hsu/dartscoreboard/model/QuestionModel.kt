package com.max.hsu.dartscoreboard.model

import com.google.gson.annotations.SerializedName
import com.max.hsu.dartscoreboard.toolUtil.DEFAULT_ABILITY

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
    val multiple : Int
) {
    Nothing(-1, "沒效果",1),Double(0, "兩倍攻擊",2),Triple(1, "三倍攻擊",3);

    companion object {
        fun from(type: Int): String = values().find { it.id == type }?.abilityName ?: DEFAULT_ABILITY
        fun getMultiple(type: Int) = values().find { it.id == type }?.multiple ?: 1
    }
}