package com.novacodestudios.donation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.novacodestudios.model.Donation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class DonationDetailViewModel @Inject constructor(

) : ViewModel() {
    var state by mutableStateOf(DonationDetailState())
        private set

    private val _eventFlow = MutableSharedFlow<UIState>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: DonationDetailEvent) {

    }

    sealed interface UIState {
        data class ShowSnackBar(val message: String) : UIState
    }
}

data class DonationDetailState(
    val isLoading: Boolean = false,
    val donation: Donation? = null
)

sealed interface DonationDetailEvent {

}