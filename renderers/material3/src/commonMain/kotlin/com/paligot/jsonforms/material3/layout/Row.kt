package com.paligot.jsonforms.material3.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun Row(
    horizontalSpacing: String?,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    androidx.compose.foundation.layout.Row(
        modifier = modifier,
        horizontalArrangement =
            horizontalSpacing
                ?.let { Arrangement.spacedBy(it.toInt().dp) }
                ?: run { Arrangement.Start },
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}
