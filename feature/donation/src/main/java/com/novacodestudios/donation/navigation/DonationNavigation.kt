package com.novacodestudios.donation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.novacodestudios.donation.detail.DonationDetailScreen
import com.novacodestudios.donation.list.DonationListScreen
import com.novacodestudios.model.screen.Screen

fun NavGraphBuilder.donationRoute(
){
    composable<Screen.DonationList>{
        DonationListScreen()
    }

    composable<Screen.DonationDetail>{
        DonationDetailScreen()
    }
}