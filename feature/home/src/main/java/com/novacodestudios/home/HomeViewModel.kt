package com.novacodestudios.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novacodestudios.data.repository.AppointmentRepository
import com.novacodestudios.data.repository.DonationRepository
import com.novacodestudios.data.repository.DonorRepository
import com.novacodestudios.data.repository.NotificationRepository
import com.novacodestudios.datastore.DonorPreferences
import com.novacodestudios.model.Appointment
import com.novacodestudios.model.Donation
import com.novacodestudios.model.Donor
import com.novacodestudios.model.Notification
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val donorRepository: DonorRepository,
    private val notificationRepository: NotificationRepository,
    private val appointmentRepository: AppointmentRepository,
    private val donationRepository: DonationRepository,
    private val preferences: DonorPreferences,
) : ViewModel() {
    var state by mutableStateOf(HomeState())
        private set

    private val _eventFlow = MutableSharedFlow<UIState>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            getDonor()
            getNotifications()
            getAppointments()
        }

    }

    private suspend fun getDonor() {
        val id = preferences.getDonorId.first() ?: return

        donorRepository.getDonor(id).onSuccess {
            state = state.copy(donor = it)
        }.onFailure {
            _eventFlow.emit(UIState.ShowSnackBar("Error getting donor"))
        }

    }

    private fun getNotifications() {
        viewModelScope.launch {
            notificationRepository.getActiveNotifications().onSuccess {
                state = state.copy(notifications = it)
            }.onFailure {
                _eventFlow.emit(UIState.ShowSnackBar("Error getting notifications"))
            }
        }
    }

    private suspend fun getAppointments() {
        appointmentRepository.getAppointments(state.donor.id).onSuccess {
            state = state.copy(activeAppointment = it.first())
        }.onFailure {
            _eventFlow.emit(UIState.ShowSnackBar("Error getting appointments"))
        }

    }

    fun onEvent(event: HomeEvent) {

    }

    sealed interface UIState {
        data class ShowSnackBar(val message: String) : UIState
    }
}

data class HomeState(
    val isLoading: Boolean = false,
    val donor: Donor=Donor(-1,"","","","","","",true),
    val notifications: List<Notification> = emptyList(),
    val activeAppointment: Appointment? = null,
    //val donations: List<Donation> = emptyList(),
)

sealed interface HomeEvent {

}