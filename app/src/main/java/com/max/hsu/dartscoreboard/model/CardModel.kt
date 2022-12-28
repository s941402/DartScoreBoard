package com.max.hsu.dartscoreboard.model

data class CardModel(
    val id: Int,
    val cardTopic: CardTopic,
    val isSelected: Boolean = false,
    val answerList: MutableSet<Int> = mutableSetOf()
)


enum class CardTopic(
    val id: Int,
    val topicName: String,
    val quantity: Int,
    val jsonFileName: String
) {
    Program(0, "程式題", 10, "engineering.json"),
    Company(1, "公司題", 5, "company.json"),
    Technology(2, "科技業題", 10, "technology.json"),
    Funny(3, "趣味題", 10, "funny.json"),
    Presentation(4, "簡報題", 15, "funny.json")
}