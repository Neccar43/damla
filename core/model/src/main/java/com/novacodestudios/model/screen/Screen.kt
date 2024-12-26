package com.novacodestudios.model.screen

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {

    @Serializable
    data object NotificationList : Screen

    @Serializable
    data object Home : Screen

    @Serializable
    data class NotificationDetail(val id: Int) : Screen

    @Serializable
    data object DonationCenterList : Screen

    @Serializable
    data class DonationCenterDetail(val id: Int) : Screen

    @Serializable
    data object Profile : Screen

    @Serializable
    data class Appointment(val centerId: Int) : Screen

    @Serializable
    data class AppointmentDetail(val id:Int) : Screen

    @Serializable
    data object Signup:Screen

    @Serializable
    data object Login:Screen

    @Serializable
    data object DonationList:Screen
    @Serializable
    data class DonationDetail(val id:Int):Screen

}