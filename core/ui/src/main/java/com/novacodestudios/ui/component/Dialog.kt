package com.novacodestudios.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun SipAlertDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    title: String,
    text: @Composable () -> Unit,
    onConfirm: () -> Unit,
    confirmButtonText: String,
    dismissButtonText: String,
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = confirmButtonText)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = dismissButtonText)
            }
        },
        title = { Text(text = title) },
        text = {
            text()
        }
    )
}