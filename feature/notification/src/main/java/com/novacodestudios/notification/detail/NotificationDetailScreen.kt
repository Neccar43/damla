package com.novacodestudios.notification.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.novacodestudios.model.DonationCenter
import com.novacodestudios.ui.component.CardDetailItem
import com.novacodestudios.ui.component.InfoCard
import com.novacodestudios.ui.component.SipButton
import kotlinx.coroutines.flow.collectLatest
import java.util.Calendar

@Composable
fun NotificationDetailScreen(
    viewModel: NotificationDetailViewModel = hiltViewModel(),
    navigateToAppointment:(centerId:Int)->Unit
) {
    val snackbarHostState =
        remember { SnackbarHostState() }

    // val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { state ->
            when (state) {
                is NotificationDetailViewModel.UIState.ShowSnackBar -> snackbarHostState.showSnackbar(
                    state.message
                )
            }
        }
    }
    NotificationDetailScreenContent(
        state = viewModel.state,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::onEvent,
        navigateToAppointment = navigateToAppointment
    )
}

@Composable
fun NotificationDetailScreenContent(
    state: NotificationDetailState,
    snackbarHostState: SnackbarHostState,
    onEvent: (NotificationDetailEvent) -> Unit,
    navigateToAppointment: (centerId: Int) -> Unit
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {}
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            InfoCard {
                state.notification.apply {
                    CardDetailItem("Başlık", title)
                    CardDetailItem("Mesaj", message)
                    CardDetailItem("Gönderilme Tarihi", sentAt)
                    CardDetailItem("Durum", if (isActive) "Aktif" else "Pasif")
                    CardDetailItem("Gerekli Kan Gurubu", requiredBloodType)
                }

            }
            DonationCenterCard(state.notification.donationCenter)
            SipButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Kabul Et",
                onClick = { navigateToAppointment(state.notification.donationCenter.id)}
            )










        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SipFullScreenDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    title: String,
    onConfirm: () -> Unit,
    confirmText: String = "Kaydet",
    content: @Composable () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            tonalElevation = AlertDialogDefaults.TonalElevation,
            color = AlertDialogDefaults.containerColor
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.clickable(onClick = onDismiss)
                    )
                    Text(
                        text = title,
                        color = AlertDialogDefaults.titleContentColor,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    Spacer(Modifier.weight(1f))
                    TextButton(
                        onClick = onConfirm,
                    ) {
                        Text(
                            text = confirmText,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(top = 24.dp)
                ) {
                    content()
                }
            }

        }
    }
}



@Composable
fun DonationCenterCard(center: DonationCenter) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        //elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        //colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = center.name, style = MaterialTheme.typography.titleMedium)
            Text(text = center.address, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = "Mesai Saatleri: ${center.openingTime} - ${center.closingTime}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )

        }
    }
}