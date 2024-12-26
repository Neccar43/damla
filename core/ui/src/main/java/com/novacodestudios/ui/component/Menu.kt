package com.novacodestudios.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SipExposedMenu(
    modifier: Modifier = Modifier,
    value: String,
    onClick: (String) -> Unit,
    label: String,
    editEnable: Boolean,
    items: List<String>,
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        SipOutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            input = TextInput(value),
            onValueChange = {},
            label = label,
            readOnly = editEnable,
            trailingIcon = if (expanded) Icons.Filled.ArrowDropUp else Icons.Default.ArrowDropDown,
        )
        ExposedDropdownMenu(
            expanded = expanded && editEnable,
            onDismissRequest = { expanded = false },
        ) {
            items.forEach {
                DropdownMenuItem(
                    text = { Text(text = it) },
                    onClick = {
                        expanded=false
                        onClick(it)
                    }
                )
            }
        }

    }
}