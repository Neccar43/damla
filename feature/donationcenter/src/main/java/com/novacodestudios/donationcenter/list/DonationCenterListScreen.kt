package com.novacodestudios.donationcenter.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.novacodestudios.model.DonationCenter
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DonationCenterListScreen(
    viewModel: DonationCenterListViewModel = koinViewModel(),
    navigateToDonationCenterDetail: (Int) -> Unit,
) {
    val snackbarHostState =
        remember { SnackbarHostState() }

    // val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { state ->
            when (state) {
                is DonationCenterListViewModel.UIState.ShowSnackBar -> snackbarHostState.showSnackbar(
                    state.message
                )
            }
        }
    }
    DonationCenterListScreenContent(
        state = viewModel.state,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::onEvent,
        navigateToDonationCenterDetail = navigateToDonationCenterDetail,
    )
}

@Composable
fun DonationCenterListScreenContent(
    state: DonationCenterListState,
    snackbarHostState: SnackbarHostState,
    onEvent: (DonationCenterListEvent) -> Unit,
    navigateToDonationCenterDetail: (Int) -> Unit,
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
            items(state.donationCenters) { donationCenter ->
                DonationCenterListItem(
                    donationCenter,
                    onClick = navigateToDonationCenterDetail,
                )
            }
        }
    }
}

@Composable
fun DonationCenterListItem(center: DonationCenter,onClick: (Int) -> Unit) {
    ListItem(
        modifier = Modifier.fillMaxWidth().background(
            shape = MaterialTheme.shapes.medium,
            color = ListItemDefaults.containerColor
        ).clickable { onClick(center.id) },
        headlineContent = { Text(text = center.name) },
        supportingContent = { Text(text = center.address) },
    )
}
