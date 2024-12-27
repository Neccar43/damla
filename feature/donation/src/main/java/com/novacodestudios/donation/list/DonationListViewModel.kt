package com.novacodestudios.donation.list

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novacodestudios.data.repository.DonationRepository
import com.novacodestudios.datastore.DonorPreferences
import com.novacodestudios.model.Donation
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.prefs.Preferences

class DonationListViewModel(
    private val  donationRepository: DonationRepository,
    private val preferences: DonorPreferences
) : ViewModel() {
    var state by mutableStateOf(DonationListState())
        private set

    private val _eventFlow = MutableSharedFlow<UIState>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            val donorId = preferences.getDonorId.first() ?: return@launch
            donationRepository.getDonations(donorId).onSuccess {
                Log.d(TAG, "getDonations: donations: $it")
                state = state.copy(donations = it)
            }.onFailure {
                Log.e(TAG, "getDonations: error: $it")
                _eventFlow.emit(UIState.ShowSnackBar("Error getting donations"))
            }
        }
    }

    fun onEvent(event: DonationListEvent) {

    }

    sealed interface UIState {
        data class ShowSnackBar(val message: String) : UIState
    }

    companion object {
        private const val TAG = "DonationListViewModel"
    }
}

data class DonationListState(
    val isLoading: Boolean = false,
    val donations: List<Donation> = emptyList()
)

sealed interface DonationListEvent {

}