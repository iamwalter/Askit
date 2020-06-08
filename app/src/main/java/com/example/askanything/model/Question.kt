package com.example.askanything.model


data class Question(
    val question: String = "",
    val options: List<Option> = listOf(),
    var authorId: String? = ""
)