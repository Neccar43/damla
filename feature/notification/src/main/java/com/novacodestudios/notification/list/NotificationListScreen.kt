package com.novacodestudios.notification.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.novacodestudios.ui.component.notification.NotificationListItem
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NotificationListScreen(
    viewModel: NotificationListViewModel = hiltViewModel(),
    navigateToNotificationDetail: (Int) -> Unit,
) {
    val snackbarHostState =
        remember { SnackbarHostState() }

    // val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { state ->
            when (state) {
                is NotificationListViewModel.UIState.ShowSnackBar -> snackbarHostState.showSnackbar(
                    state.message
                )
            }
        }
    }
    NotificationListScreenContent(
        state = viewModel.state,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::onEvent,
        navigateToNotificationDetail = navigateToNotificationDetail,
    )
}

@Composable
fun NotificationListScreenContent(
    state: NotificationListState,
    snackbarHostState: SnackbarHostState,
    onEvent: (NotificationListEvent) -> Unit,
    navigateToNotificationDetail: (Int) -> Unit
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
            items(state.notifications) { notification ->
                NotificationListItem(
                    notification = notification,
                    onClick = navigateToNotificationDetail
                )
            }

        }
    }
}
