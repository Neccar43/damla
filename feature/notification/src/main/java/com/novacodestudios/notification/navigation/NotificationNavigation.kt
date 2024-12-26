package com.novacodestudios.notification.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.novacodestudios.model.screen.Screen
import com.novacodestudios.notification.detail.NotificationDetailScreen
import com.novacodestudios.notification.list.NotificationListScreen

fun NavGraphBuilder.notificationRoute(
    navigateToNotificationDetail: (Int) -> Unit,
    navigateToAppointment:(centerId:Int)->Unit
){
    composable<Screen.NotificationList>{
        NotificationListScreen(
            navigateToNotificationDetail = navigateToNotificationDetail
        )
    }

    composable<Screen.NotificationDetail>{
        NotificationDetailScreen(
            navigateToAppointment = navigateToAppointment
        )
    }
}