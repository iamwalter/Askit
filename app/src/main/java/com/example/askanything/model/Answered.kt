package com.example.askanything.model

data class Answered(
    var uid: String? = "",
    var answered: List<String>? = emptyList()
)