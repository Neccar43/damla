package com.novacodestudios.notification.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novacodestudios.data.repository.NotificationRepository
import com.novacodestudios.model.Notification
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationListViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) : ViewModel() {
    var state by mutableStateOf(NotificationListState())
        private set

    private val _eventFlow = MutableSharedFlow<UIState>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            notificationRepository.getActiveNotifications()
                .onSuccess { notifications ->
                    state = state.copy(
                        notifications = notifications
                    )
                }
                .onFailure { error ->
                    _eventFlow.emit(UIState.ShowSnackBar(error.message ?: "An error occurred"))
                }
        }
    }

    fun onEvent(event: NotificationListEvent) {

    }

    sealed interface UIState {
        data class ShowSnackBar(val message: String) : UIState
    }
}

data class NotificationListState(
    val notifications: List<Notification> = emptyList(),
    val isLoading: Boolean = false
)

sealed interface NotificationListEvent {

}