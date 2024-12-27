package com.novacodestudios.appointment.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.novacodestudios.appointment.appointment.AppointmentScreen
import com.novacodestudios.appointment.detail.AppointmentDetailScreen
import com.novacodestudios.model.screen.Screen


fun NavGraphBuilder.appointmentRoute(
    navigateToHome: () -> Unit,
    navigateBack: () -> Unit
){
    composable<Screen.Appointment>{
        AppointmentScreen(
            navigateToHome =navigateToHome
        )
    }

    composable<Screen.AppointmentDetail>{
        AppointmentDetailScreen(
            navigateBack = navigateBack
        )
    }
}