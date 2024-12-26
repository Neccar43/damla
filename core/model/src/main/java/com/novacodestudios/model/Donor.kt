package com.novacodestudios.model

import kotlinx.serialization.Serializable

@Serializable
data class DonorLoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class AddDonor(
    val name: String,
    val email: String,
    val password: String,
    val phone: String,
    val bloodGroup: String,
    val lastDonationDate: String?,
)

@Serializable
data class UpdateDonor(
    val id: Int,
    val name: String? = null,
    val email: String? = null,
    val password: String?=null,
    val phone: String? = null,
    val bloodGroup: String? = null,
    val lastDonationDate: String? = null,
)

@Serializable
data class Donor(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val phone: String,
    val bloodGroup: String,
    val lastDonationDate: String?,
    val isSuitable: Boolean
)