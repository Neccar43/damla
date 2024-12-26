package com.novacodestudios.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.novacodestudios.model.screen.Screen
import com.novacodestudios.profile.ProfileScreen

fun NavGraphBuilder.profileRoute(
){
    composable<Screen.Profile>{
        ProfileScreen()
    }
}