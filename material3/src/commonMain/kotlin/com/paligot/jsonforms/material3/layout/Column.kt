package com.paligot.jsonforms.material3.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun Column(
    verticalSpacing: String?,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    androidx.compose.foundation.layout.Column(
        modifier = modifier,
        verticalArrangement = verticalSpacing
            ?.let { Arrangement.spacedBy(it.toInt().dp) }
            ?: run { Arrangement.Top },
        content = content
    )
}
