package com.novacodestudios.notification.detail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.novacodestudios.data.repository.NotificationRepository
import com.novacodestudios.model.DonationCenter
import com.novacodestudios.model.Notification
import com.novacodestudios.model.screen.Screen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


class NotificationDetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val notificationRepository: NotificationRepository,
) : ViewModel() {
    var state by mutableStateOf(NotificationDetailState())
        private set

    private val _eventFlow = MutableSharedFlow<UIState>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            notificationRepository.getNotification(savedStateHandle.toRoute<Screen.NotificationDetail>().id)
                .onSuccess {
                    Log.d(TAG, "Notification: $it")
                    state = state.copy(
                        isLoading = false,
                        notification = it
                    )
                }.onFailure {
                    Log.e(TAG, "Error getting notification", it)
                    _eventFlow.emit(UIState.ShowSnackBar(it.message ?: "Error"))
                }
        }
    }

    fun onEvent(event: NotificationDetailEvent) {
        when (event) {
            NotificationDetailEvent.OnAcceptClick -> acceptNotification()
            is NotificationDetailEvent.OnDateSelected -> state = state.copy(selectedDate = event.date)
        }
    }

    private fun acceptNotification() {
        viewModelScope.launch {

        }
    }

    sealed interface UIState {
        data class ShowSnackBar(val message: String) : UIState
    }

    companion object {
        private const val TAG = "NotificationDetailViewModel"
    }
}

data class NotificationDetailState(
    val isLoading: Boolean = true,
    val notification: Notification = Notification(
        -1,
        "",
        "",
        "",
        true,
        "",
        DonationCenter(-1, "", "", 0.0, 0.0, "", "")
    ),
    val selectedDate: Long = System.currentTimeMillis(),
)

sealed interface NotificationDetailEvent {
    data object OnAcceptClick : NotificationDetailEvent
    data class OnDateSelected(val date: Long) : NotificationDetailEvent
}