package com.paligot.jsonforms.material3.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paligot.jsonforms.kotlin.internal.ext.value
import com.paligot.jsonforms.kotlin.models.schema.Property
import com.paligot.jsonforms.kotlin.models.uischema.Orientation
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RadioButton(
    value: String?,
    values: ImmutableList<Property>,
    orientation: Orientation = Orientation.HORIZONTALLY,
    modifier: Modifier = Modifier,
    label: String? = null,
    description: String? = null,
    error: String? = null,
    enabled: Boolean = true,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    onValueChange: (String) -> Unit
) {
    Column(modifier = modifier) {
        label?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.titleMedium
            )
        }
        description?.let { Text(text = it) }
        if (orientation == Orientation.VERTICALLY) {
            FlowColumn(
                verticalArrangement = verticalArrangement,
                horizontalArrangement = horizontalArrangement
            ) {
                values.forEach {
                    RadioButton(
                        selected = value == it.const?.value<String>(),
                        onClick = { onValueChange.invoke(it.const?.value() ?: "") },
                        endContent = { Text(text = it.title ?: "") },
                        enabled = enabled
                    )
                }
            }
        } else {
            FlowRow(
                verticalArrangement = verticalArrangement,
                horizontalArrangement = horizontalArrangement
            ) {
                values.forEach {
                    RadioButton(
                        selected = value == it.const?.value(),
                        onClick = { onValueChange.invoke(it.const?.value() ?: "") },
                        endContent = { Text(text = it.title ?: "") },
                        enabled = enabled
                    )
                }
            }
        }
        error?.let {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
internal fun RadioButton(
    selected: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    endContent: @Composable (() -> Unit)? = null
) {
    val clickableModifier = if (onClick != null) {
        modifier.clickable { onClick() }
    } else {
        modifier
    }
    Row(
        modifier = clickableModifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        androidx.compose.material3.RadioButton(
            selected = selected,
            onClick = if (onClick != null && endContent == null) {
                onClick
            } else {
                null
            },
            modifier = modifier,
            enabled = enabled
        )
        endContent?.let { it() }
    }
}
