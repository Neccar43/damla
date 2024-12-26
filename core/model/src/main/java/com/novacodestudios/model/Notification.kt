package com.novacodestudios.model

import kotlinx.serialization.Serializable

@Serializable
data class Notification(
    val id: Int,
    val title: String,
    val message: String,
    val sentAt: String,
    val isActive: Boolean,
    val requiredBloodType: String,
    val donationCenter: DonationCenter
)