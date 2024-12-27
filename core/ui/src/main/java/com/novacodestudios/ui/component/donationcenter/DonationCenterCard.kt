package com.novacodestudios.ui.component.donationcenter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.novacodestudios.model.DonationCenter

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