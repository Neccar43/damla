package com.novacodestudios.damla

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bloodtype
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.novacodestudios.damla.navigation.DamlaNavHost
import com.novacodestudios.datastore.DonorPreferences
import com.novacodestudios.model.screen.Screen
import com.novacodestudios.ui.theme.DamlaTheme
import kotlinx.coroutines.flow.first
import org.koin.android.ext.android.inject
import org.koin.java.KoinJavaComponent.inject


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DamlaTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val isMainGraph = mainScreens.any { navBackStackEntry?.destination?.hasRoute(it::class) == true }

                LaunchedEffect(Unit) {
                    val preferences by inject<DonorPreferences>()

                    val donorId = preferences.getDonorId.first()
                    if (donorId != null && donorId != -1) {
                        navController.navigate(Screen.Home)
                        return@LaunchedEffect
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (isMainGraph) {
                            BottomBar(navController = navController)
                        }

                    },

                    topBar = {
                        val route= navBackStackEntry?.destination?.route
                        val title= toScreenName(route)?: return@Scaffold
                        if (isMainGraph){
                            CenterAlignedTopAppBar(title={ Text(title) })
                        }else{
                            TopAppBar(title = { Text(title) }, navigationIcon = {
                                IconButton(
                                    onClick = {navController.popBackStack()}
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Back"
                                    )
                                }
                            })
                        }
                    }
                    ) { innerPadding ->
                    DamlaNavHost(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .consumeWindowInsets(innerPadding),
                        navHostController = navController
                    )
                }
            }
        }
    }
}

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val route: Screen
)

val mainScreens = listOf(
    Screen.Home,
    Screen.DonationList,
    Screen.NotificationList,
    Screen.Profile
)


@Composable
fun BottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val navItems= listOf(
        BottomNavigationItem(
            title = "Ana Sayfa",
            selectedIcon = Icons.Filled.Home,
            unSelectedIcon = Icons.Outlined.Home,
            route = Screen.Home
        ),
        BottomNavigationItem(
            title = "Bağişlar",
            selectedIcon = ImageVector.vectorResource(id = R.drawable.fluid_filled_24px),
            unSelectedIcon = ImageVector.vectorResource(id = R.drawable.fluid_24px),
            route = Screen.DonationList
        ),

        BottomNavigationItem(
            title = "Bildirimler",
            selectedIcon = Icons.Filled.Notifications,
            unSelectedIcon = Icons.Outlined.Notifications,
            route = Screen.NotificationList
        ),
        BottomNavigationItem(
            title = "Profil",
            selectedIcon = Icons.Filled.Person,
            unSelectedIcon = Icons.Outlined.Person,
            route = Screen.Profile
        ),
    )

    NavigationBar {
        navItems.forEach { item ->
            val isSelected = currentDestination?.hasRoute(item.route::class) == true

            NavigationBarItem(
                label = { Text(text = item.title) },
                alwaysShowLabel = false,
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) {
                            item.selectedIcon
                        } else {
                            item.unSelectedIcon
                        },
                        contentDescription = item.title
                    )
                })
        }
    }
}

fun toScreenName(route: String?): String? = when(route?.substringAfterLast(".")){
    "Home" -> "Damla"
    "DonationCenterList" -> "Merkezler"
    "NotificationList" -> "Bildirimler"
    "Profile" -> "Profil"
    "DonationCenterDetail/{id}" -> "Merkez Detay"
    "NotificationDetail/{id}" -> "Bildirim Detay"
    "Appointment/{centerId}" -> "Randevu"
    "AppointmentDetail/{id}" -> "Randevu Detay"
    "Signup" -> null
    "Login" -> null
    "DonationList" -> "Bağışlar"
    "DonationDetail/{id}" -> "Bağış Detay"
    else -> {
        Log.e("MainActivity", "Unknown route: $route")
        null
    }
}

