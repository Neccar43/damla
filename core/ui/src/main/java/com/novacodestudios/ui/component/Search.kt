package com.novacodestudios.ui.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SipSearchBar(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    query: String,
    // onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    placeHolder: String = "Arama yapın",
    content: @Composable ColumnScope.() -> Unit
) {
    SearchBar(
        modifier = modifier,
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = onSearch,
                onSearch = onSearch,
                expanded = expanded,
                onExpandedChange = onExpandedChange,
                placeholder = { Text(placeHolder) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            )
        },
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        content = content
    )
}