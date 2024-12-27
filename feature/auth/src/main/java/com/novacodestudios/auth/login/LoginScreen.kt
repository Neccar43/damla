package com.novacodestudios.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.novacodestudios.ui.component.SipButton
import com.novacodestudios.ui.component.SipEmailField
import com.novacodestudios.ui.component.SipPasswordField
import com.novacodestudios.ui.component.SipTextButton
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
    navigateToHome: () -> Unit,
    navigateToSignup: () -> Unit,
) {
    val snackbarHostState =
        remember { SnackbarHostState() }

    // val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { state ->
            when (state) {
                is LoginViewModel.UIState.ShowSnackBar -> snackbarHostState.showSnackbar(state.message)
                LoginViewModel.UIState.NavigateToHome -> navigateToHome()
            }
        }
    }
    LoginScreenContent(
        state = viewModel.state,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::onEvent,
        navigateToSignup = navigateToSignup
    )
}

@Composable
fun LoginScreenContent(
    state: LoginState,
    snackbarHostState: SnackbarHostState,
    onEvent: (LoginEvent) -> Unit,
    navigateToSignup: () -> Unit
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
                text = "Giriş Yap",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(bottom = 24.dp),
                color = MaterialTheme.colorScheme.primary
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SipEmailField(
                    input = state.emailInput,
                    onValueChange = { onEvent(LoginEvent.OnEmailChanged(it)) },
                    modifier = Modifier.fillMaxWidth(),
                )
                SipPasswordField(
                    input = state.passwordInput,
                    onValueChange = { onEvent(LoginEvent.OnPasswordChanged(it)) },
                    modifier = Modifier.fillMaxWidth(),
                )

                SipButton(
                    text = "Giriş Yap",
                    onClick = { onEvent(LoginEvent.OnLoginClicked) },
                    modifier = Modifier.fillMaxWidth(),
                )

                SipTextButton(
                    text = "Henüz hesabınız yok mu? Kayıt olun",
                    onClick = { navigateToSignup()},
                    modifier = Modifier.fillMaxWidth(),
                )
            }




        }
    }
}

@Composable
fun LoginScreenContent2(
    state: LoginState,
    snackbarHostState: SnackbarHostState,
    onEvent: (LoginEvent) -> Unit,
    navigateToSignup: () -> Unit
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {}
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                //.background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Header text
                Text(
                    text = "Giriş Yap",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Card for form
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        SipEmailField(
                            input = state.emailInput,
                            onValueChange = { onEvent(LoginEvent.OnEmailChanged(it)) },
                            modifier = Modifier.fillMaxWidth(),
                        )
                        SipPasswordField(
                            input = state.passwordInput,
                            onValueChange = { onEvent(LoginEvent.OnPasswordChanged(it)) },
                            modifier = Modifier.fillMaxWidth(),
                        )

                        SipButton(
                            text = "Giriş Yap",
                            onClick = { onEvent(LoginEvent.OnLoginClicked) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                        )

                        HorizontalDivider(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .fillMaxWidth(),
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                        )

                        SipTextButton(
                            text = "Henüz hesabınız yok mu? Kayıt olun",
                            onClick = { navigateToSignup() },
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }
        }
    }
}

