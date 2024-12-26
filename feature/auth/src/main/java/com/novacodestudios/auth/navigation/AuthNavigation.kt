package com.novacodestudios.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.novacodestudios.auth.login.LoginScreen
import com.novacodestudios.auth.signup.SignupScreen
import com.novacodestudios.model.screen.Screen


fun NavGraphBuilder.authRoute(
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToSignup: () -> Unit
){
    composable<Screen.Signup>{
        SignupScreen(
            navigateToHome = navigateToHome,
            navigateToLogin = navigateToLogin
        )
    }

    composable<Screen.Login>{
        LoginScreen(
            navigateToHome = navigateToHome,
            navigateToSignup = navigateToSignup
        )
    }
}