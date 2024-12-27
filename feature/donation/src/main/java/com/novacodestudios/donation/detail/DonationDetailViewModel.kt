package com.novacodestudios.donation.detail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.novacodestudios.data.repository.DonationRepository
import com.novacodestudios.model.Donation
import com.novacodestudios.model.screen.Screen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


class DonationDetailViewModel(
    private val donationRepository: DonationRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    var state by mutableStateOf(DonationDetailState())
        private set

    private val _eventFlow = MutableSharedFlow<UIState>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            val donationId = savedStateHandle.toRoute<Screen.DonationDetail>().id
            donationRepository.getDonation(donationId).onSuccess {
                Log.d(TAG, "getDonation: donation: $it")
                state = state.copy(donation = it)
            }.onFailure {
                Log.e(TAG, "getDonation: error", it)
                _eventFlow.emit(UIState.ShowSnackBar("Error getting donation"))
            }
        }
    }

    fun onEvent(event: DonationDetailEvent) {

    }

    sealed interface UIState {
        data class ShowSnackBar(val message: String) : UIState
    }

    companion object {
        private const val TAG = "DonationDetailViewModel"
    }
}

data class DonationDetailState(
    val isLoading: Boolean = false,
    val donation: Donation? = null
)

sealed interface DonationDetailEvent {

}