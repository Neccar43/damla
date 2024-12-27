package com.novacodestudios.appointment.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.novacodestudios.data.repository.AppointmentRepository
import com.novacodestudios.model.Appointment
import com.novacodestudios.model.AppointmentStatus
import com.novacodestudios.model.UpdateAppointment
import com.novacodestudios.model.screen.Screen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


class AppointmentDetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val appointmentRepository: AppointmentRepository
) : ViewModel() {
    var state by mutableStateOf(
        AppointmentDetailState(
            appointmentId = savedStateHandle.toRoute<Screen.AppointmentDetail>().id
        )
    )
        private set

    private val _eventFlow = MutableSharedFlow<UIState>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            appointmentRepository.getAppointmentById(state.appointmentId)
                .onFailure {
                    state = state.copy(isLoading = false)
                    _eventFlow.emit(UIState.ShowSnackBar("Failed to load appointment"))
                }.onSuccess {
                    state = state.copy(isLoading = false, appointment = it)
                }
        }
    }

    fun onEvent(event: AppointmentDetailEvent) {
        when (event) {
            AppointmentDetailEvent.OnCancelClicked -> cancelAppointment()
            AppointmentDetailEvent.OnSubmitEditClicked -> submitEdit()
        }
    }

    private fun submitEdit() {// TODO: Implement edit appointment

    }

    private fun cancelAppointment() {
        viewModelScope.launch {
            appointmentRepository.updateAppointment(
                state.appointment!!.copy(status = AppointmentStatus.CANCELED).toUpdateAppointment()
            )
                .onFailure { _eventFlow.emit(UIState.ShowSnackBar("Failed to cancel appointment")) }
                .onSuccess {
                    _eventFlow.emit(UIState.NavigateBack)
                }
        }
    }

    sealed interface UIState {
        data class ShowSnackBar(val message: String) : UIState
        data object NavigateBack : UIState
    }
}

data class AppointmentDetailState(
    val isLoading: Boolean = false,
    val appointment: Appointment? = null,
    val appointmentId: Int
)

sealed interface AppointmentDetailEvent {
    data object OnSubmitEditClicked : AppointmentDetailEvent
    data object OnCancelClicked : AppointmentDetailEvent
}

fun Appointment.toUpdateAppointment() = UpdateAppointment(
    id = id,
    appointmentDate = date,
    donorId = donor.id,
    centerId = donationCenter.id,
    status = status
)