package com.novacodestudios.donation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.novacodestudios.model.Donation
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DonationListScreen(
    viewModel: DonationListViewModel = koinViewModel(),
    navigateToDonationDetail: (Int) -> Unit
) {
    val snackbarHostState =
        remember { SnackbarHostState() }

    // val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { state ->
            when (state) {
                is DonationListViewModel.UIState.ShowSnackBar -> snackbarHostState.showSnackbar(
                    state.message
                )
            }
        }
    }
    DonationListScreenContent(
        state = viewModel.state,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::onEvent,
        navigateToDonationDetail = navigateToDonationDetail
    )
}

@Composable
fun DonationListScreenContent(
    state: DonationListState,
    snackbarHostState: SnackbarHostState,
    onEvent: (DonationListEvent) -> Unit,
    navigateToDonationDetail: (Int) -> Unit
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {}
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            items(state.donations) {
                DonationListItem(donation = it, onClick = navigateToDonationDetail)
            }
        }
    }
}


@Composable
fun DonationListItem(donation: Donation, onClick: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .padding(16.dp)
            .clickable { onClick(donation.id) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Başarı Durumu için Renkli İkon
        val statusColor = if (donation.successful) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.error
        }
        Icon(
            imageVector = if (donation.successful) Icons.Default.Check else Icons.Default.Close,
            contentDescription = null,
            tint = statusColor,
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = statusColor.copy(alpha = 0.1f),
                    shape = CircleShape
                )
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // İçerik Bölümü
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = donation.donationDate,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = donation.donationCenter.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Başarı veya Başarısızlık Durumu
        Text(
            text = if (donation.successful) "Başarılı" else "Başarısız",
            color = statusColor,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

