package com.novacodestudios.damla.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.novacodestudios.appointment.navigation.appointmentRoute
import com.novacodestudios.auth.navigation.authRoute
import com.novacodestudios.donation.navigation.donationRoute
import com.novacodestudios.donationcenter.navigation.donationCenterRoute
import com.novacodestudios.home.navigation.homeRoute
import com.novacodestudios.model.screen.Screen
import com.novacodestudios.navigation.profileRoute
import com.novacodestudios.notification.navigation.notificationRoute

@Composable
fun DamlaNavHost(
    modifier: Modifier,
    navHostController: NavHostController,
) {
    NavHost(
        startDestination = Screen.Login,
        navController = navHostController,
        modifier = modifier
    ) {
        appointmentRoute(
            navigateToHome ={navHostController.navigate(Screen.Home)}
        )
        authRoute(
            navigateToHome ={navHostController.navigate(Screen.Home)},
            navigateToLogin ={navHostController.navigate(Screen.Login)},
            navigateToSignup ={navHostController.navigate(Screen.Signup)}
        )
        donationRoute()

        donationCenterRoute(
            navigateToDonationCenterDetail ={navHostController.navigate(Screen.DonationCenterDetail(it))},
            navigateAppointment ={navHostController.navigate(Screen.Appointment(it))}
        )
        homeRoute(
            navigateToNotificationDetail ={navHostController.navigate(Screen.NotificationDetail(it))},
            navigateToAppointmentDetail ={navHostController.navigate(Screen.AppointmentDetail(it))},
            navigateToDonationDetail ={navHostController.navigate(Screen.DonationDetail(it))},
            navigateToProfile ={navHostController.navigate(Screen.Profile)}
        )
        notificationRoute(
            navigateToNotificationDetail ={navHostController.navigate(Screen.NotificationDetail(it))},
            navigateToAppointment ={navHostController.navigate(Screen.Appointment(it))}
        )
        profileRoute()
    }
}