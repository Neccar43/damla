package com.novacodestudios.donationcenter.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.novacodestudios.donationcenter.detail.DonationCenterDetailScreen
import com.novacodestudios.donationcenter.list.DonationCenterListScreen
import com.novacodestudios.model.screen.Screen

fun NavGraphBuilder.donationCenterRoute(
    navigateToDonationCenterDetail: (Int) -> Unit,
    navigateAppointment: (Int) -> Unit,
){
    composable<Screen.DonationCenterList>{
        DonationCenterListScreen(
            navigateToDonationCenterDetail = navigateToDonationCenterDetail
        )
    }

    composable<Screen.DonationCenterDetail>{
        DonationCenterDetailScreen(
            navigateAppointment = navigateAppointment
        )
    }
}