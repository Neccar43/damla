package com.novacodestudios.donation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DonationDetailScreen(
    viewModel: DonationDetailViewModel = hiltViewModel()
) {
    val snackbarHostState =
        remember { SnackbarHostState() }

    // val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { state ->
            when (state) {
                is DonationDetailViewModel.UIState.ShowSnackBar -> snackbarHostState.showSnackbar(
                    state.message
                )
            }
        }
    }
    DonationDetailScreenContent(
        state = viewModel.state,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::onEvent,
    )
}

@Composable
fun DonationDetailScreenContent(
    state: DonationDetailState,
    snackbarHostState: SnackbarHostState,
    onEvent: (DonationDetailEvent) -> Unit,
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

        }
    }
}
