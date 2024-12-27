package com.novacodestudios.auth.signup

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novacodestudios.data.repository.DonorRepository
import com.novacodestudios.datastore.DonorPreferences
import com.novacodestudios.model.AddDonor
import com.novacodestudios.ui.component.TextInput
import com.novacodestudios.ui.component.validateConfirmPassword
import com.novacodestudios.ui.component.validateEmail
import com.novacodestudios.ui.component.validateInput
import com.novacodestudios.ui.component.validatePassword
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


class SignupViewModel(
    private val repository: DonorRepository,
    private val preferences: DonorPreferences,
) : ViewModel() {
    var state by mutableStateOf(SignupState())
        private set

    private val _eventFlow = MutableSharedFlow<UIState>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: SignupEvent) {
        when (event) {
            is SignupEvent.OnBloodGroupChanged -> state = state.copy(bloodGroupInput = state.bloodGroupInput.copy(value = event.bloodGroup))
            is SignupEvent.OnConfirmPasswordChanged -> state = state.copy(confirmPasswordInput = state.confirmPasswordInput.copy(value = event.confirmPassword))
            is SignupEvent.OnEmailChanged -> state = state.copy(emailInput = state.emailInput.copy(value = event.email))
            is SignupEvent.OnNameChanged -> state = state.copy(nameInput = state.nameInput.copy(value = event.name))
            is SignupEvent.OnPasswordChanged -> state = state.copy(passwordInput = state.passwordInput.copy(value = event.password))
            is SignupEvent.OnPhoneChanged -> state = state.copy(phoneInput = state.phoneInput.copy(value = event.phone))
            SignupEvent.OnSignupClicked -> signup()
        }
    }

    private fun signup() {
        viewModelScope.launch {
            val confirmPasswordInput = state.confirmPasswordInput.validateConfirmPassword(state.passwordInput.value)
            val emailInput = state.emailInput.validateEmail()
            val passwordInput = state.passwordInput.validatePassword()
            val nameInput = state.nameInput.validateInput("Ad")
            val phoneInput = state.phoneInput.validateInput("Telefon")
            val bloodGroupInput = state.bloodGroupInput.validateInput("Kan Grubu")

            val hasError= listOf(
                confirmPasswordInput,
                emailInput,
                passwordInput,
                nameInput,
                phoneInput,
                bloodGroupInput,
            ).any { it.error != null }

            if (hasError) {
                state = state.copy(
                    confirmPasswordInput = confirmPasswordInput,
                    emailInput = emailInput,
                    passwordInput = passwordInput,
                    nameInput = nameInput,
                    phoneInput = phoneInput,
                    bloodGroupInput = bloodGroupInput,
                )
                return@launch
            }
            val addDonor= AddDonor(
                email = emailInput.value!!,
                password = passwordInput.value!!,
                name = nameInput.value!!,
                phone = phoneInput.value!!,
                bloodGroup = bloodGroupInput.value!!,
                lastDonationDate = null,
            )
            Log.d(TAG, "signup: addDonor :$addDonor")
            state = state.copy(isLoading = true)

            repository.signUp(addDonor).onFailure {
                state = state.copy(isLoading = false)
                Log.e(TAG, "signup: kayıt sıarsında bir hata oluştu ", it)
                _eventFlow.emit(UIState.ShowSnackBar(it.localizedMessage ?: "Bir hata oluştu"))
            }.onSuccess {
                state = state.copy(isLoading = false)
                Log.d(TAG, "signup: kayıt başarılı :$it")
                preferences.setDonorId(it.id)
                _eventFlow.emit(UIState.NavigateToHome)
            }
        }
    }


    sealed interface UIState {
        data class ShowSnackBar(val message: String) : UIState
        data object NavigateToHome : UIState
    }

    companion object {
        private const val TAG = "SignupViewModel"
    }
}

data class SignupState(
    val isLoading: Boolean = false,
    val emailInput: TextInput<String> = TextInput("zahit@gmail.com"),
    val passwordInput: TextInput<String> = TextInput("Zahit.25"),
    val confirmPasswordInput: TextInput<String> = TextInput("Zahit.25"),
    val nameInput: TextInput<String> = TextInput("Zahit Kaya"),
    val phoneInput: TextInput<String> = TextInput("0532 123 45 67"),
    val bloodGroupInput: TextInput<String> = TextInput("AB Rh+"),


    )

sealed interface SignupEvent {
    data object OnSignupClicked : SignupEvent
    data class OnEmailChanged(val email: String) : SignupEvent
    data class OnPasswordChanged(val password: String) : SignupEvent
    data class OnConfirmPasswordChanged(val confirmPassword: String) : SignupEvent
    data class OnNameChanged(val name: String) : SignupEvent
    data class OnPhoneChanged(val phone: String) : SignupEvent
    data class OnBloodGroupChanged(val bloodGroup: String) : SignupEvent

}

/*fun Throwable.toUiText(): String {
    return when (this) {
        is java.net.UnknownHostException -> "İnternet bağlantınızı kontrol edin"
        is java.net.SocketTimeoutException -> "Sunucuya bağlanırken zaman aşımına uğradı. Lütfen tekrar deneyin."
        is retrofit2.HttpException -> {
            // HTTP hata kodlarına göre farklı mesajlar
            when (this.code()) {
                400 -> "Geçersiz istek. Lütfen verilerinizi kontrol edin."
                401 -> "Yetkilendirme hatası. Lütfen giriş yapın."
                403 -> "Erişim engellendi. Bu işlemi gerçekleştirmek için yeterli izniniz yok."
                404 -> "Aradığınız kaynak bulunamadı."
                500 -> "Sunucu hatası. Lütfen daha sonra tekrar deneyin."
                502 -> "Geçici bir sunucu hatası oluştu. Lütfen tekrar deneyin."
                503 -> "Sunucu geçici olarak kullanılamıyor. Lütfen daha sonra tekrar deneyin."
                504 -> "Zaman aşımı hatası. Lütfen tekrar deneyin."
                else -> "Beklenmeyen bir hata oluştu. Lütfen tekrar deneyin."
            }
        }
        is java.io.IOException -> "Ağ hatası. Lütfen internet bağlantınızı kontrol edin."
        is java.lang.NullPointerException -> "Bir şeyler yanlış gitti. Lütfen tekrar deneyin."
        else -> "Bilinmeyen bir hata oluştu"
    }
}*/
