package com.novacodestudios.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.novacodestudios.model.Appointment
import com.novacodestudios.model.Donor
import com.novacodestudios.ui.component.CardDetailItem
import com.novacodestudios.ui.component.EmptyStateMessage
import com.novacodestudios.ui.component.EmptyStateMessageWithButton
import com.novacodestudios.ui.component.notification.NotificationListItem
import com.novacodestudios.util.formatDateTime
import com.novacodestudios.util.toUiText
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    navigateToNotificationDetail: (Int) -> Unit,
    navigateToAppointmentDetail: (Int) -> Unit,
    navigateToDonationDetail: (Int) -> Unit,
    navigateToProfile: () -> Unit,
    navigateToDonationCenterList: () -> Unit,
) {
    val snackbarHostState =
        remember { SnackbarHostState() }

    // val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { state ->
            when (state) {
                is HomeViewModel.UIState.ShowSnackBar -> snackbarHostState.showSnackbar(state.message)
            }
        }
    }
    HomeScreenContent(
        state = viewModel.state,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::onEvent,
        navigateToNotificationDetail = navigateToNotificationDetail,
        navigateToAppointmentDetail = navigateToAppointmentDetail,
        navigateToDonationDetail = navigateToDonationDetail,
        navigateToProfile = navigateToProfile,
        navigateToDonationCenterList = navigateToDonationCenterList
    )
}

@Composable
fun HomeScreenContent(
    state: HomeState,
    snackbarHostState: SnackbarHostState,
    onEvent: (HomeEvent) -> Unit,
    navigateToNotificationDetail: (Int) -> Unit,
    navigateToAppointmentDetail: (Int) -> Unit,
    navigateToDonationDetail: (Int) -> Unit,
    navigateToProfile: () -> Unit,
    navigateToDonationCenterList: () -> Unit,
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {}
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                DonorInfoCard(state.donor)
            }
            item {
                SectionHeader("Bildirimler")
                Spacer(modifier = Modifier.height(8.dp))
                if (state.notifications.isEmpty()) {
                    EmptyStateMessage(message = "Şu anda bildirim yok", icon = Icons.Default.Notifications)
                } else {
                    state.notifications.forEach {
                        NotificationListItem(it, navigateToNotificationDetail)
                    }
                }


            }

                item {
                    SectionHeader("Randevular")
                    Spacer(modifier = Modifier.height(8.dp))
                    state.activeAppointment?.let {
                        AppointmentInfoCard(
                            appointment = it,
                            onClick = { navigateToAppointmentDetail(it.id) }
                        )
                    }?: EmptyStateMessageWithButton(
                        message = "Şu anda randevunuz yok",
                        onClick = { navigateToDonationCenterList() },
                        buttonText = "Randevu Al",
                    )

                }


        }
    }
}



@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        //color = MaterialTheme.colorScheme.primary
    )

}


@Composable
fun DonorInfoCard(donor: Donor) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),

        //elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        //colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            //Text(text = donor.name, style = MaterialTheme.typography.titleLarge)
            CardDetailItem("İsim", donor.name)
            CardDetailItem("Kan Gurubu", donor.bloodGroup)
            donor.lastDonationDate?.let { CardDetailItem("Son Bağış Tarhi", it) }
            CardDetailItem("Durum", if (donor.isSuitable) "Bağışa Uygun" else "Bağışa Uygun Değil")
        }
    }
}


@Composable
fun AppointmentInfoCard(appointment: Appointment, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        onClick = onClick,
        //elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            //Text(text = donor.name, style = MaterialTheme.typography.titleLarge)

            CardDetailItem("Tarih", formatDateTime(appointment.date))
            CardDetailItem("Merkez", appointment.donationCenter.name)
            CardDetailItem("Durum", appointment.status.toUiText())
        }
    }
}




