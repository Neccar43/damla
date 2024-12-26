package com.novacodestudios.model

import kotlinx.serialization.Serializable

@kotlinx.serialization.Serializable
data class AddAnswer(
    val questionId: Int,
    val appointmentId: Int,
    val answerText: String,
        )

@kotlinx.serialization.Serializable
data class UpdateAnswer(
    val id: Int,
    val questionId: Int?=null,
    val appointmentId: Int?=null,
    val answerText: String?=null,
)

@Serializable
data class Answer(
    val id: Int,
    val question: Question,
    val appointment: Appointment,
    val answerText: String,
)