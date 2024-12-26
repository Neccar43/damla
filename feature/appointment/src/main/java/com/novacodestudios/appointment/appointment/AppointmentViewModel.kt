package com.novacodestudios.appointment.appointment

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.novacodestudios.data.repository.AppointmentRepository
import com.novacodestudios.data.repository.QuestionRepository
import com.novacodestudios.datastore.DonorPreferences
import com.novacodestudios.model.AddAppointment
import com.novacodestudios.model.AddAppointmentRequest
import com.novacodestudios.model.InsertAnswer
import com.novacodestudios.model.screen.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val questionRepository: QuestionRepository,
    private val appointmentRepository: AppointmentRepository,
    private val preferences: DonorPreferences,
    private val savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
    var state by mutableStateOf(
        AppointmentState(
        centerId = savedStateHandle.toRoute<Screen.Appointment>().centerId
    )
    )
        private set

    private val _eventFlow = MutableSharedFlow<UIState>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            questionRepository.getQuestions().onFailure {
                Log.e(TAG, "Error getting questions", it)
                _eventFlow.emit(UIState.ShowSnackBar(it.message ?: "Error"))
            }.onSuccess { questions->
                Log.d(TAG, "Questions: $questions")
                state = state.copy(questionWithAnswers = questions.map { QuestionWithAnswer(it.id, it.questionText, "") })
            }
        }
    }

    fun onEvent(event: AppointmentEvent) {
        when (event) {
            AppointmentEvent.OnAcceptAnswersClick -> acceptAnswer()
            is AppointmentEvent.OnDateSelected -> state = state.copy(selectedDate = event.date)
            is AppointmentEvent.OnQuestionAnswered -> {
                state = state.copy(
                    questionWithAnswers = state.questionWithAnswers.map {
                        if (it.id == event.questionId) {
                            it.copy(answer = event.answer)
                        } else {
                            it
                        }
                    }
                )
            }

            AppointmentEvent.OnSubmitAppointmentClick -> submitAppointment()
            is AppointmentEvent.OnTimePickerVisibleChanged -> state = state.copy(isTimePickerVisible = event.isVisible)
        }
    }

    private fun submitAppointment() {
        viewModelScope.launch {
            val addAppointment=AddAppointment(
                appointmentDate = state.selectedDate.toString(),
                donorId = preferences.getDonorId.first()!!,
                centerId = state.centerId,
            )

            val addAppointmentRequest=AddAppointmentRequest(
                appointment = addAppointment,
                answers = state.questionWithAnswers.map { InsertAnswer(
                    questionId = it.id,
                    answerText = it.answer
                ) }
            )

            appointmentRepository.addAppointment(addAppointmentRequest)
                .onFailure {
                    Log.e(TAG, "Error adding appointment", it)
                    _eventFlow.emit(UIState.ShowSnackBar(it.message ?: "Error"))
                }.onSuccess {
                    Log.d(TAG, "Appointment added: $it")
                    _eventFlow.emit(UIState.ShowSnackBar("Randevu başarıyla oluşturuldu"))
                    _eventFlow.emit(UIState.NavigateToHome)
                }
        }
    }

    private fun acceptAnswer() {
        viewModelScope.launch {
            if(state.questionWithAnswers.any { !it.isAnswered }) {
                _eventFlow.emit(UIState.ShowSnackBar("Lütfen tüm soruları cevaplayınız"))
                return@launch
            }
            state = state.copy(isTimePickerVisible = true)
        }
    }

    sealed interface UIState {
        data class ShowSnackBar(val message: String) : UIState
        data object NavigateToHome : UIState
    }

    companion object {
        private const val TAG = "AppointmentViewModel"
    }
}

data class AppointmentState(
    val isLoading: Boolean = false,
    val questionWithAnswers:List<QuestionWithAnswer> = emptyList(),
    val selectedDate: Long = System.currentTimeMillis(),
    val isTimePickerVisible: Boolean = false,
    val centerId: Int,
)

sealed interface AppointmentEvent {
    data object OnAcceptAnswersClick : AppointmentEvent
    data object OnSubmitAppointmentClick : AppointmentEvent
    data class OnDateSelected(val date: Long) : AppointmentEvent
    data class OnQuestionAnswered(val questionId: Int, val answer: String) : AppointmentEvent
    data class OnTimePickerVisibleChanged(val isVisible: Boolean) : AppointmentEvent

}

data class QuestionWithAnswer(
    val id: Int,
    val question: String,
    val answer: String,
) {
    val isAnswered: Boolean
        get() = answer.isNotEmpty()
}