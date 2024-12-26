package com.novacodestudios.appointment.appointment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.novacodestudios.ui.component.SipButton
import com.novacodestudios.ui.component.SipTextField
import com.novacodestudios.ui.component.TextInput
import kotlinx.coroutines.flow.collectLatest
import java.util.Calendar

@Composable
fun AppointmentScreen(
    viewModel: AppointmentViewModel = hiltViewModel(),
    navigateToHome: () -> Unit
) {
    val snackbarHostState =
        remember { SnackbarHostState() }

    // val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { state ->
            when (state) {
                is AppointmentViewModel.UIState.ShowSnackBar -> snackbarHostState.showSnackbar(state.message)
                AppointmentViewModel.UIState.NavigateToHome -> navigateToHome()
            }
        }
    }
    AppointmentScreenContent(
        state = viewModel.state,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::onEvent,
    )
}

@Composable
fun AppointmentScreenContent(
    state: AppointmentState,
    snackbarHostState: SnackbarHostState,
    onEvent: (AppointmentEvent) -> Unit,
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {}
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(state.questionWithAnswers) { question ->
                QuestionItem(
                    question = question,
                    onAnswer = { answer ->
                        onEvent(AppointmentEvent.OnQuestionAnswered(question.id, answer))
                    }
                )

            }
            item {
                SipButton(
                    text = "Onayla",
                    onClick = { onEvent(AppointmentEvent.OnAcceptAnswersClick) },
                    modifier = Modifier.fillMaxWidth()
                )
            }

        }

        if (state.isTimePickerVisible) {
            TimePickerDialog(
                onDismiss = { onEvent(AppointmentEvent.OnTimePickerVisibleChanged(false)) },
                onTimeSelected = {
                    onEvent(AppointmentEvent.OnDateSelected(it))
                    onEvent(AppointmentEvent.OnSubmitAppointmentClick)
                    onEvent(AppointmentEvent.OnTimePickerVisibleChanged(false))
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onTimeSelected: (Long) -> Unit
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                val calendar = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                    set(Calendar.MINUTE, timePickerState.minute)
                }
                onTimeSelected(calendar.timeInMillis)
            }) {
                Text("Tamam")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss()
            }) {
                Text("İptal")
            }
        },
        text = {
            TimeInput(
                state = timePickerState,
            )
        }
    )
}

@Composable
fun QuestionItem(
    question: QuestionWithAnswer,
    onAnswer: (String) -> Unit
) {
    Column {
        Text(
            text = question.question,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        SipTextField(
            modifier = Modifier.fillMaxWidth(),
            input = TextInput(
                value = question.answer,
            ),
            onValueChange = { onAnswer(it) },
        )
    }
}
