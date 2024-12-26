package com.novacodestudios.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.novacodestudios.model.Appointment
import com.novacodestudios.model.AppointmentStatus
import com.novacodestudios.model.Donor
import com.novacodestudios.ui.component.CardDetailItem
import com.novacodestudios.ui.component.notification.NotificationListItem
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToNotificationDetail: (Int) -> Unit,
    navigateToAppointmentDetail: (Int) -> Unit,
    navigateToDonationDetail: (Int) -> Unit,
    navigateToProfile: () -> Unit,
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
                    //EmptyStateMessage(message = "Şu anda bildirim yok")
                } else {
                    state.notifications.forEach {
                        NotificationListItem(it, navigateToNotificationDetail)
                    }
                }


            }
            state.activeAppointment?.let {
                item {
                    SectionHeader("Randevular")
                    Spacer(modifier = Modifier.height(8.dp))
                    AppointmentInfoCard(
                        appointment = it,
                        onClick = { navigateToAppointmentDetail(it.id) }
                    )
                }
            }


            /*items(state.donations){

            }*/


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
        onClick = onClick
        //elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        //colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            //Text(text = donor.name, style = MaterialTheme.typography.titleLarge)
            CardDetailItem("Tarih", appointment.date)
            CardDetailItem("Merkez", appointment.donationCenter.name)
            CardDetailItem("Durum", appointment.status.toUiText())
        }
    }
}

fun AppointmentStatus.toUiText(): String = when (this) {
    AppointmentStatus.SCHEDULED -> "Randevu Alındı"
    AppointmentStatus.COMPLETED -> "Tamamlandı"
    AppointmentStatus.CANCELED -> "İptal Edildi"
}


