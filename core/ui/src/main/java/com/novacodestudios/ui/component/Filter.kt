package com.novacodestudios.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun FilterOrderRow(
    modifier: Modifier = Modifier,
    onFilterClick: () -> Unit,
    onSortClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.weight(1f))
        IconTextButton(
            icon = Icons.AutoMirrored.Filled.Sort,
            text = "Sırala",
            onClick = onSortClick
        )
        IconTextButton(
            icon = Icons.Default.FilterList,
            text = "Filtrele",
            onClick = onFilterClick
        )
    }
}

@Composable
fun IconTextButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        //dim
        val color = MaterialTheme.colorScheme.outline
        Icon(imageVector = icon, contentDescription = null, tint = color)
        Text(text = text, color = color, style = MaterialTheme.typography.labelLarge)
    }
}



@Composable
fun SheetItem(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text, style = MaterialTheme.typography.bodyLarge)
    }
}