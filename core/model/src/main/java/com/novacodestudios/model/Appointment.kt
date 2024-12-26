package com.novacodestudios.model

import kotlinx.serialization.Serializable

@Serializable
data class AddAppointment(
    val appointmentDate: String,
    val donorId: Int,
    val centerId: Int,
    val status: AppointmentStatus = AppointmentStatus.SCHEDULED,
)

@Serializable
data class AddAppointmentRequest(
    val appointment: AddAppointment,
    val answers: List<InsertAnswer>
)
@Serializable
data class InsertAnswer(
    val questionId: Int,
    val answerText: String,
)

@Serializable
data class UpdateAppointment(
    val id: Int,
    val appointmentDate: String? = null,
    val donorId: Int? = null,
    val centerId: Int? = null,
    val status: AppointmentStatus? = null,
)
@Serializable
data class Appointment(
    val id: Int,
    val date: String,
    val donor: Donor,
    val donationCenter: DonationCenter,
    val status: AppointmentStatus,
)

enum class AppointmentStatus {
    SCHEDULED, COMPLETED, CANCELED
}