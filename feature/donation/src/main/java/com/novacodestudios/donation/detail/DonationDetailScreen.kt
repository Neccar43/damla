package com.novacodestudios.donation.detail

import android.app.Person
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
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
import com.novacodestudios.model.Personnel
import com.novacodestudios.ui.component.CardDetailItem
import com.novacodestudios.ui.component.InfoCard
import com.novacodestudios.ui.component.donationcenter.DonationCenterCard
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DonationDetailScreen(
    viewModel: DonationDetailViewModel = koinViewModel()
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
                .padding(16.dp)
        ) {
            state.donation?: return@Scaffold

            InfoCard {
                CardDetailItem("İsim", state.donation.donor.name)
                CardDetailItem("Bağış Tarihi", state.donation.donationDate)
                CardDetailItem("Bağış Durumu", if(state.donation.successful) "Başarılı" else "Başarısız")
                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                Text("Notlar", style = MaterialTheme.typography.titleSmall)
                Text(state.donation.remarks)
            }
            Text(
                text = "Merkez",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            DonationCenterCard(state.donation.donationCenter)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Personel",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            PersonnelCenterCard(state.donation.personnel)
        }
    }
}

@Composable
fun PersonnelCenterCard(personnel: Personnel) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        //elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        //colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = personnel.name, style = MaterialTheme.typography.titleMedium)
            Text(text = personnel.role, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = personnel.email,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )

        }
    }
}



