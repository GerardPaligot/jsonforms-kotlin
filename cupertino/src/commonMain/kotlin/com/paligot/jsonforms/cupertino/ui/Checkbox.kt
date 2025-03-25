package com.paligot.jsonforms.cupertino.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.slapps.cupertino.CupertinoCheckBox
import com.slapps.cupertino.CupertinoText

@Composable
fun Checkbox(
    value: Boolean,
    modifier: Modifier = Modifier,
    label: String? = null,
    enabled: Boolean = true,
    onCheckedChange: ((Boolean) -> Unit),
) {
    Row(
        modifier = if (label != null) {
            modifier.clickable { onCheckedChange(!value) }
        } else {
            modifier
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        CupertinoCheckBox(
            checked = value,
            onCheckedChange = if (label == null) {
                { onCheckedChange(it) }
            } else {
                null
            },
            enabled = enabled
        )
        if (label != null) {
            CupertinoText(text = label)
        }
    }
}
