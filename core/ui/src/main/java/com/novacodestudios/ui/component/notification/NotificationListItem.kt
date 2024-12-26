package com.novacodestudios.ui.component.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.novacodestudios.model.Notification

@Composable
fun NotificationListItem(notification: Notification, onClick: (Int) -> Unit) {
    ListItem(
        modifier = Modifier.fillMaxWidth().background(
            shape = MaterialTheme.shapes.medium,
            color = ListItemDefaults.containerColor
        ).clickable { onClick(notification.id) },
        headlineContent = { Text(text = notification.title) },
        supportingContent = { Text(text = notification.message) },
    )
}