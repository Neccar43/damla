package com.novacodestudios.donationcenter.list

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novacodestudios.data.repository.DonationCenterRepository
import com.novacodestudios.model.DonationCenter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DonationCenterListViewModel @Inject constructor(
    private val repository: DonationCenterRepository
) : ViewModel() {
    var state by mutableStateOf(DonationCenterListState())
        private set

    private val _eventFlow = MutableSharedFlow<UIState>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            repository.getDonationCenters()
                .onFailure {
                    state = state.copy(isLoading = false)
                    Log.e(TAG, "Error getting donation centers", it)
                    _eventFlow.emit(UIState.ShowSnackBar(it.message ?: "Error"))
                }.onSuccess {
                    state = state.copy(isLoading = false)
                    Log.d(TAG, "Donation Centers: $it")
                    state = state.copy(donationCenters = it)
                }
        }
    }

    fun onEvent(event: DonationCenterListEvent) {

    }

    sealed interface UIState {
        data class ShowSnackBar(val message: String) : UIState
    }

    companion object {
        private const val TAG = "DonationCenterListViewModel"
    }
}

data class DonationCenterListState(
    val isLoading: Boolean = false,
    val donationCenters: List<DonationCenter> = emptyList()
)

sealed interface DonationCenterListEvent {

}