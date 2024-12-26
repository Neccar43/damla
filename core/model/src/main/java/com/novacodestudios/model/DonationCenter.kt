package com.novacodestudios.model

import kotlinx.serialization.Serializable

@Serializable
data class DonationCenter(
    val id: Int,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val openingTime:String,
    val closingTime: String,
)