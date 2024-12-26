package com.novacodestudios.donationcenter.detail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.novacodestudios.data.repository.DonationCenterRepository
import com.novacodestudios.model.DonationCenter
import com.novacodestudios.model.screen.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DonationCenterDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: DonationCenterRepository,
) : ViewModel() {
    var state by mutableStateOf(DonationCenterDetailState())
        private set

    private val _eventFlow = MutableSharedFlow<UIState>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            val centerId = savedStateHandle.toRoute<Screen.DonationCenterDetail>().id
            repository.getDonationCenter(centerId)
                .onFailure {
                    Log.e(TAG, "Error getting donation center", it)
                    _eventFlow.emit(UIState.ShowSnackBar(it.message ?: "Error"))
                }.onSuccess {
                    Log.d(TAG, "Donation Center: $it")
                    state = state.copy(donationCenter = it)
                }
        }


    }

    fun onEvent(event: DonationCenterDetailEvent) {

    }

    private fun makeAppointment() {

    }

    sealed interface UIState {
        data class ShowSnackBar(val message: String) : UIState
    }

    companion object {
        private const val TAG = "DonationCenterDetailViewModel"
    }
}

data class DonationCenterDetailState(
    val isLoading: Boolean = false,
    val donationCenter: DonationCenter? = null
)

sealed interface DonationCenterDetailEvent {
}