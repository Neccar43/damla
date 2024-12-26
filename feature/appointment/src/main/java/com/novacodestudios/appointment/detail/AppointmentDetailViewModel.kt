package com.novacodestudios.appointment.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.novacodestudios.model.Appointment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class AppointmentDetailViewModel @Inject constructor(

) : ViewModel() {
    var state by mutableStateOf(AppointmentDetailState())
        private set

    private val _eventFlow = MutableSharedFlow<UIState>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: AppointmentDetailEvent) {

    }

    sealed interface UIState {
        data class ShowSnackBar(val message: String) : UIState
    }
}

data class AppointmentDetailState(
    val isLoading: Boolean = false,
    val appointment: Appointment? = null
)

sealed interface AppointmentDetailEvent {

}