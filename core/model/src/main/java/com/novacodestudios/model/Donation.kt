package com.novacodestudios.model

import kotlinx.serialization.Serializable

@Serializable
data class Donation(
    val id: Int,
    val appointment: Appointment,
    val personnel: Personnel,
    val donor: Donor,
    val successful: Boolean,
    val donationDate: String,
    val remarks: String,
    val donationCenter: DonationCenter,
)