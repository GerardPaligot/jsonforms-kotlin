package com.paligot.jsonforms.kotlin.panes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FormListPane(
    modifier: Modifier = Modifier,
    onAccountCreationClick: () -> Unit,
    onLogInClick: () -> Unit,
    onContactClick: () -> Unit,
    onAddressClick: () -> Unit,
    onAppleClick: () -> Unit
) {
    Scaffold(modifier = modifier) {
        LazyColumn(contentPadding = it) {
            item {
                ListItem(
                    headlineContent = { Text(text = "Account creation") },
                    modifier = Modifier.clickable(onClick = onAccountCreationClick)
                )
            }
            item {
                ListItem(
                    headlineContent = { Text(text = "Log In") },
                    modifier = Modifier.clickable(onClick = onLogInClick)
                )
            }
            item {
                ListItem(
                    headlineContent = { Text(text = "Contact") },
                    modifier = Modifier.clickable(onClick = onContactClick)
                )
            }
            item {
                ListItem(
                    headlineContent = { Text(text = "Address") },
                    modifier = Modifier.clickable(onClick = onAddressClick)
                )
            }
            item {
                ListItem(
                    headlineContent = { Text(text = "Apple Form") },
                    modifier = Modifier.clickable(onClick = onAppleClick)
                )
            }
        }
    }
}
