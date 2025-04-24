package com.paligot.jsonforms.material3.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Switch(
    value: Boolean,
    modifier: Modifier,
    label: String? = null,
    description: String? = null,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f),
            content = {
                val hasLabel = !label.isNullOrEmpty()
                val hasDescription = !description.isNullOrEmpty()
                if (!hasLabel && hasDescription) {
                    description?.let { Text(text = it) }
                } else {
                    Text(
                        text = label ?: "",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    if (hasDescription) {
                        description?.let { Text(text = it) }
                    }
                }
            },
        )

        androidx.compose.material3.Switch(
            checked = value,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
        )
    }
}
