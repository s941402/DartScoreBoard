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
) {
    Double(0, "兩倍攻擊"),Triple(1, "三倍攻擊");

    companion object {
        fun from(type: Int): String = values().find { it.id == type }?.abilityName ?: DEFAULT_ABILITY
    }
}