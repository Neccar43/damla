package com.novacodestudios.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.novacodestudios.home.HomeScreen
import com.novacodestudios.model.screen.Screen

fun NavGraphBuilder.homeRoute(
    navigateToNotificationDetail: (Int) -> Unit,
    navigateToAppointmentDetail: (Int) -> Unit,
    navigateToDonationDetail: (Int) -> Unit,
    navigateToProfile: () -> Unit,
    navigateToDonationCenterList: () -> Unit
){
    composable<Screen.Home>{
        HomeScreen(
            navigateToNotificationDetail = navigateToNotificationDetail,
            navigateToAppointmentDetail = navigateToAppointmentDetail,
            navigateToDonationDetail = navigateToDonationDetail,
            navigateToProfile = navigateToProfile,
            navigateToDonationCenterList = navigateToDonationCenterList
        )
    }
}