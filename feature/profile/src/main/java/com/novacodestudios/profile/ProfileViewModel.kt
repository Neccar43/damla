package com.novacodestudios.profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novacodestudios.data.repository.DonorRepository
import com.novacodestudios.datastore.DonorPreferences
import com.novacodestudios.model.Donor
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class ProfileViewModel(
    private val donorRepository: DonorRepository,
    private val preferences: DonorPreferences,
) : ViewModel() {
    var state by mutableStateOf(ProfileState())
        private set

    private val _eventFlow = MutableSharedFlow<UIState>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            val donorId = preferences.getDonorId.first() ?: return@launch
            donorRepository.getDonor(donorId).onSuccess {
                Log.d(TAG, "getDonor: donor: $it")
                state = state.copy(donor = it)
            }.onFailure {
                Log.e(TAG, "getDonor: error", it)
                _eventFlow.emit(UIState.ShowSnackBar("Error getting donor"))
            }
        }
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.OnLogoutClicked -> {
                viewModelScope.launch {
                    preferences.setDonorId(-1)
                    _eventFlow.emit(UIState.NavigateToLogin)
                }
            }
        }
    }

    sealed interface UIState {
        data class ShowSnackBar(val message: String) : UIState
        data object NavigateToLogin : UIState
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}

data class ProfileState(
    val isLoading: Boolean = false,
    val donor: Donor? = null
)

sealed interface ProfileEvent {
    data object OnLogoutClicked : ProfileEvent
}