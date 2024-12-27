package com.novacodestudios.donation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.novacodestudios.donation.detail.DonationDetailScreen
import com.novacodestudios.donation.list.DonationListScreen
import com.novacodestudios.model.screen.Screen

fun NavGraphBuilder.donationRoute(
    navigateToDonationDetail: (Int) -> Unit
){
    composable<Screen.DonationList>{
        DonationListScreen(
            navigateToDonationDetail = navigateToDonationDetail
        )
    }

    composable<Screen.DonationDetail>{
        DonationDetailScreen()
    }
}