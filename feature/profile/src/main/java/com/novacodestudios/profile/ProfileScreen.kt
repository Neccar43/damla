package com.novacodestudios.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.novacodestudios.model.Donor
import com.novacodestudios.ui.component.CardDetailItem
import com.novacodestudios.ui.component.InfoCard
import com.novacodestudios.ui.theme.DamlaTheme
import com.novacodestudios.util.formatDateTime
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = koinViewModel(),
    navigateToLogin: () -> Unit,
) {
    val snackbarHostState =
        remember { SnackbarHostState() }

    // val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { state ->
            when (state) {
                is ProfileViewModel.UIState.ShowSnackBar -> snackbarHostState.showSnackbar(state.message)
                ProfileViewModel.UIState.NavigateToLogin -> navigateToLogin()
            }
        }
    }
    ProfileScreenContent(
        state = viewModel.state,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::onEvent,
    )
}

@Composable
fun ProfileScreenContent(
    state: ProfileState,
    snackbarHostState: SnackbarHostState,
    onEvent: (ProfileEvent) -> Unit,
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
            ProfileCard(donor = state.donor ?: return@Scaffold)
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            SettingsItem(
                name = "Bilgilerimi Güncelle",
                icon = Icons.Default.Person,
                onClick = {}
            )
            SettingsItem(
                name = "Şifremi Unuttum",
                icon = Icons.Default.Password,
                onClick = {}
            )
            SettingsItem(
                name = "Çıkış Yap",
                icon = Icons.AutoMirrored.Filled.Logout,
                onClick = {onEvent(ProfileEvent.OnLogoutClicked) }
            )

        }
    }
}

@Composable
fun SettingsItem(name:String,icon:ImageVector,onClick:()->Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon,contentDescription = null)
        Spacer(modifier = Modifier.width(16.dp))
        Text(name)
    }
}

@Composable
fun ProfileCard(donor: Donor) {
    InfoCard {
        CardDetailItem("Ad",donor.name)
        CardDetailItem("Email",donor.email)
        CardDetailItem("Telefon",donor.phone)
        CardDetailItem("Kan Grubu",donor.bloodGroup)
        CardDetailItem("Son Kan Bağış Tarihi",formatDateTime(donor.lastDonationDate))
        if (donor.isSuitable) Text("Kan Bağışına Uygun", color = MaterialTheme.colorScheme.primary)
        else Text("Kan Bağışına Uygun Değil", color = MaterialTheme.colorScheme.error)
    }
}





