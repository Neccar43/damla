package com.novacodestudios.auth.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bloodtype
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.novacodestudios.auth.login.LoginEvent
import com.novacodestudios.ui.component.SipButton
import com.novacodestudios.ui.component.SipEmailField
import com.novacodestudios.ui.component.SipOutlinedTextField
import com.novacodestudios.ui.component.SipPasswordField
import com.novacodestudios.ui.component.SipTextButton
import com.novacodestudios.ui.component.SipTextField
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignupScreen(
    viewModel: SignupViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit,
) {
    val snackbarHostState =
        remember { SnackbarHostState() }

    // val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { state ->
            when (state) {
                is SignupViewModel.UIState.ShowSnackBar -> snackbarHostState.showSnackbar(state.message)
                SignupViewModel.UIState.NavigateToHome -> navigateToHome()
            }
        }
    }
    SignupScreenContent(
        state = viewModel.state,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::onEvent,
        navigateToLogin = navigateToLogin
    )
}

@Composable
fun SignupScreenContent(
    state: SignupState,
    snackbarHostState: SnackbarHostState,
    onEvent: (SignupEvent) -> Unit,
    navigateToLogin: () -> Unit
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {}
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Kayıt Ol",
                style = MaterialTheme.typography.headlineLarge.copy(color = MaterialTheme.colorScheme.primary),
                modifier = Modifier.padding(bottom = 24.dp),
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SipOutlinedTextField(
                    label = "Ad Soyad",
                    input = state.nameInput,
                    onValueChange = { onEvent(SignupEvent.OnNameChanged(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = Icons.Default.Person
                )
                SipOutlinedTextField(
                    input = state.phoneInput,
                    onValueChange = { onEvent(SignupEvent.OnPhoneChanged(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardType = KeyboardType.Phone,
                    label = "Telefon Numarası",
                    leadingIcon = Icons.Default.Phone
                )
                SipOutlinedTextField(
                    input = state.bloodGroupInput,
                    onValueChange = { onEvent(SignupEvent.OnBloodGroupChanged(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    label = "Kan Grubu",
                    leadingIcon = Icons.Default.Bloodtype
                )

                SipEmailField(
                    input = state.emailInput,
                    onValueChange = { onEvent(SignupEvent.OnEmailChanged(it)) },
                    modifier = Modifier.fillMaxWidth(),
                )
                SipPasswordField(
                    input = state.passwordInput,
                    onValueChange = { onEvent(SignupEvent.OnPasswordChanged(it)) },
                    modifier = Modifier.fillMaxWidth(),
                )
                SipPasswordField(
                    input = state.confirmPasswordInput,
                    onValueChange = { onEvent(SignupEvent.OnConfirmPasswordChanged(it)) },
                    modifier = Modifier.fillMaxWidth(),
                )


                SipButton(
                    text = "Kayıt Ol",
                    onClick = { onEvent(SignupEvent.OnSignupClicked) },
                    modifier = Modifier.fillMaxWidth(),
                )

                SipTextButton(
                    text = "Hesabınız var mı? Giriş yapın",
                    onClick = { navigateToLogin() },
                    modifier = Modifier.fillMaxWidth(),
                )
            }




        }
    }
}
