package com.novacodestudios.appointment.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.novacodestudios.model.Appointment
import com.novacodestudios.ui.component.CardDetailItem
import com.novacodestudios.ui.component.InfoCard
import com.novacodestudios.ui.component.SipButton
import com.novacodestudios.util.formatDateTime
import com.novacodestudios.util.toUiText
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppointmentDetailScreen(
    viewModel: AppointmentDetailViewModel = koinViewModel(),
    navigateBack: () -> Unit,
) {
    val snackbarHostState =
        remember { SnackbarHostState() }

    // val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { state ->
            when (state) {
                is AppointmentDetailViewModel.UIState.ShowSnackBar -> snackbarHostState.showSnackbar(
                    state.message
                )

                AppointmentDetailViewModel.UIState.NavigateBack -> navigateBack()
            }
        }
    }
    AppointmentDetailScreenContent(
        state = viewModel.state,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::onEvent,
    )
}

@Composable
fun AppointmentDetailScreenContent(
    state: AppointmentDetailState,
    snackbarHostState: SnackbarHostState,
    onEvent: (AppointmentDetailEvent) -> Unit,
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {}
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            AppointmentCard(state.appointment) {
                onEvent(AppointmentDetailEvent.OnCancelClicked)
            }

        }
    }
}

@Composable
fun AppointmentCard(appointment: Appointment?,onCancelClicked: () -> Unit) {
    appointment?.apply {
        InfoCard {
            CardDetailItem("Tarih", formatDateTime(date))
            CardDetailItem("Durum", status.toUiText())
            donationCenter.apply {
                CardDetailItem("Merkez", name)
                CardDetailItem("Adres", address)
                CardDetailItem("Enlem", latitude.toString())
                CardDetailItem("Boylam", longitude.toString())
                CardDetailItem("Mesai Saatleri", "$openingTime - $closingTime")
            }

            SipButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Randevuyu İptal Et",
                onClick = onCancelClicked
            )
        }
    }
}
