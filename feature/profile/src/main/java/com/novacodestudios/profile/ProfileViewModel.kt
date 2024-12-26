package com.novacodestudios.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(

) : ViewModel() {
    var state by mutableStateOf(ProfileState())
        private set

    private val _eventFlow = MutableSharedFlow<UIState>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: ProfileEvent) {

    }

    sealed interface UIState {
        data class ShowSnackBar(val message: String) : UIState
    }
}

data class ProfileState(
    val isLoading: Boolean = false,
)

sealed interface ProfileEvent {

}