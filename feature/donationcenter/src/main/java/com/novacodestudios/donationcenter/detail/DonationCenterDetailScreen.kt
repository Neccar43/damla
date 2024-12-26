package com.novacodestudios.donationcenter.detail

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.novacodestudios.model.screen.Screen
import com.novacodestudios.ui.component.CardDetailItem
import com.novacodestudios.ui.component.InfoCard
import com.novacodestudios.ui.component.SipButton
import com.novacodestudios.ui.component.isNotNull
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DonationCenterDetailScreen(
    viewModel: DonationCenterDetailViewModel = hiltViewModel(),
    navigateAppointment: (Int) -> Unit,
) {
    val snackbarHostState =
        remember { SnackbarHostState() }

    // val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { state ->
            when (state) {
                is DonationCenterDetailViewModel.UIState.ShowSnackBar -> snackbarHostState.showSnackbar(
                    state.message
                )
            }
        }
    }
    DonationCenterDetailScreenContent(
        state = viewModel.state,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::onEvent,
        navigateAppointment = navigateAppointment
    )
}

@Composable
fun DonationCenterDetailScreenContent(
    state: DonationCenterDetailState,
    snackbarHostState: SnackbarHostState,
    onEvent: (DonationCenterDetailEvent) -> Unit,
    navigateAppointment: (Int)->Unit
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {}
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (state.donationCenter==null) return@Scaffold

                InfoCard {
                    state.donationCenter.apply {
                        CardDetailItem("Merkez",name)
                        CardDetailItem("Adres",address)
                        CardDetailItem("Enlem",latitude.toString())
                        CardDetailItem("Boylam",longitude.toString())
                        CardDetailItem("Mesai Saatleri","$openingTime - $closingTime")
                    }
                    SipButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Randevu Al",
                        onClick = {navigateAppointment(state.donationCenter.id)}
                    )
                }
        }
    }
}
