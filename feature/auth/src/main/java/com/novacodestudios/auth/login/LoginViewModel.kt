package com.novacodestudios.auth.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novacodestudios.data.repository.DonorRepository
import com.novacodestudios.datastore.DonorPreferences
import com.novacodestudios.model.DonorLoginRequest
import com.novacodestudios.ui.component.TextInput
import com.novacodestudios.ui.component.validateConfirmPassword
import com.novacodestudios.ui.component.validateEmail
import com.novacodestudios.ui.component.validateInput
import com.novacodestudios.ui.component.validatePassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: DonorRepository,
    private val preferences: DonorPreferences,
) : ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    private val _eventFlow = MutableSharedFlow<UIState>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChanged -> state = state.copy(emailInput = state.emailInput.copy(value = event.email))
            LoginEvent.OnLoginClicked -> login()
            is LoginEvent.OnPasswordChanged -> state = state.copy(passwordInput = state.passwordInput.copy(value = event.password))
        }
    }

    private fun login() {
        viewModelScope.launch {
            val emailInput = state.emailInput.validateEmail()
            val passwordInput = state.passwordInput.validatePassword()

            val hasError= listOf(
                emailInput,
                passwordInput,
            ).any { it.error != null }

            if (hasError) {
                state = state.copy(
                    emailInput = emailInput,
                    passwordInput = passwordInput,
                )
                return@launch
            }

            state = state.copy(isLoading = true)
            val request=DonorLoginRequest(
                email = state.emailInput.value!!,
                password = state.passwordInput.value!!,
            )

            repository.login(request).onSuccess {
                Log.d(TAG, "login: $it")
                state = state.copy(isLoading = false)
                preferences.setDonorId(it.id)
                _eventFlow.emit(UIState.NavigateToHome)
            }.onFailure {
                Log.e(TAG, "login: girş işleminde bir hata oluştu", it)
                state = state.copy(isLoading = false)
                _eventFlow.emit(UIState.ShowSnackBar(it.message ?: "Bir hata oluştu"))
            }
        }
    }

    sealed interface UIState {
        data class ShowSnackBar(val message: String) : UIState
        data object NavigateToHome : UIState
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}

data class LoginState(
    val isLoading: Boolean = false,
    val emailInput: TextInput<String> = TextInput("zahit@gmail.com"),
    val passwordInput: TextInput<String> = TextInput("Zahit.25"),
)

sealed interface LoginEvent {
    data class OnEmailChanged(val email: String) : LoginEvent
    data class OnPasswordChanged(val password: String) : LoginEvent
    data object OnLoginClicked : LoginEvent
}