package com.paligot.jsonforms.material3.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize
import com.paligot.jsonforms.kotlin.internal.ext.value
import com.paligot.jsonforms.kotlin.models.schema.Property
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.filter

@Composable
fun OutlinedDropdown(
    value: String?,
    values: ImmutableList<Property>,
    modifier: Modifier = Modifier,
    label: String? = null,
    isError: Boolean = false,
    enabled: Boolean = true,
    onValueChange: (String) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }
    OutlinedDropdown(
        value = value
            ?.let { values.find { it.const?.value<String>() == value }?.title ?: "" }
            ?: "",
        modifier = modifier,
        label = label ?: "",
        enabled = enabled,
        expanded = expanded,
        isError = isError,
        children = {
            values.forEach {
                ListItem(
                    headlineContent = { Text(text = it.title ?: "") },
                    modifier = Modifier.clickable {
                        onValueChange(it.const?.value() ?: "").also {
                            expanded.value = false
                        }
                    }
                )
            }
        }
    )
}

@Composable
internal fun OutlinedDropdown(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    expanded: MutableState<Boolean> = remember { mutableStateOf(false) },
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    children: @Composable ColumnScope.() -> Unit
) {
    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }
    Dropdown(
        expanded = expanded,
        anchor = {
            OutlinedTextField(
                value = value,
                onValueChange = {},
                modifier = Modifier.fillMaxWidth().onGloballyPositioned {
                    mTextFieldSize = it.size.toSize()
                },
                label = { Text(text = label) },
                readOnly = true,
                isError = isError,
                singleLine = true,
                maxLines = 1,
                enabled = enabled,
                interactionSource = interactionSource
            )
        },
        modifier = modifier.width(with(LocalDensity.current) { mTextFieldSize.width.toDp() }),
        children = children,
        interactionSource = interactionSource
    )
}

@Composable
internal fun Dropdown(
    anchor: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    expanded: MutableState<Boolean> = remember { mutableStateOf(false) },
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onDismissRequest: () -> Unit = {},
    children: @Composable ColumnScope.() -> Unit
) {
    LaunchedEffect(interactionSource) {
        interactionSource.interactions
            .filter { it is PressInteraction.Press }
            .collect {
                expanded.value = !expanded.value
            }
    }

    Box {
        anchor()
        DropdownMenu(
            modifier = modifier,
            expanded = expanded.value,
            onDismissRequest = {
                onDismissRequest()
                expanded.value = false
            },
            content = children
        )
    }
}
