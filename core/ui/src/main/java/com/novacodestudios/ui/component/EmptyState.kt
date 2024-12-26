package com.novacodestudios.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun EmptyStateMessage(
    modifier: Modifier = Modifier,
    message: String,
    icon: ImageVector = Icons.Default.Info,
    iconTint: Color = MaterialTheme.colorScheme.primary,
    messageStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    iconSize: Dp = 64.dp,
    spacing: Dp = 16.dp
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(spacing)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(iconSize)
            )
            Spacer(modifier = Modifier.height(spacing))
            Text(
                text = message,
                style = messageStyle,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun RefreshStateMessage(
    modifier: Modifier = Modifier,
    message: String,
    icon: ImageVector = Icons.Default.Info,
    iconTint: Color = MaterialTheme.colorScheme.primary,
    messageStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    iconSize: Dp = 64.dp,
    spacing: Dp = 16.dp,
    onRefresh: () -> Unit,
    refreshText: String = "Yenile"
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(spacing)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(iconSize)
            )
            Spacer(modifier = Modifier.height(spacing))
            Text(
                text = message,
                style = messageStyle,
                textAlign = TextAlign.Center
            )
            Button(
                onClick = onRefresh,
                modifier = Modifier.padding(top = spacing)
            ) {
                Text(text = refreshText)
            }
        }
    }
}