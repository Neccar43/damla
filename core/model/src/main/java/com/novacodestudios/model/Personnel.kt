package com.novacodestudios.model

import kotlinx.serialization.Serializable

@Serializable
data class Personnel(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val role: String,
    val donationCenter: DonationCenter,
)