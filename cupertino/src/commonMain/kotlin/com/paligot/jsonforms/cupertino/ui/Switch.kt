package com.paligot.jsonforms.cupertino.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.slapps.cupertino.CupertinoSwitch
import com.slapps.cupertino.CupertinoText
import com.slapps.cupertino.theme.CupertinoTheme

@Composable
fun Switch(
    value: Boolean,
    modifier: Modifier,
    label: String? = null,
    description: String? = null,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            content = {
                val hasLabel = !label.isNullOrEmpty()
                val hasDescription = !description.isNullOrEmpty()
                if (!hasLabel && hasDescription) {
                    description?.let { CupertinoText(text = it) }
                } else {
                    CupertinoText(
                        text = label ?: "",
                        style = CupertinoTheme.typography.headline
                    )
                    if (hasDescription) {
                        description?.let { CupertinoText(text = it) }
                    }
                }
            }
        )

        CupertinoSwitch(
            checked = value,
            onCheckedChange = onCheckedChange,
            enabled = enabled
        )
    }
}
