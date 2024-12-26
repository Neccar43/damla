package com.novacodestudios.model

import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val id: Int,
    val questionText: String,
    )