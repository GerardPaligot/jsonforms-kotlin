package com.paligot.jsonforms.cupertino.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.paligot.jsonforms.kotlin.models.schema.PropertyValue
import com.slapps.cupertino.CupertinoText
import com.slapps.cupertino.CupertinoWheelPicker
import com.slapps.cupertino.ExperimentalCupertinoApi
import com.slapps.cupertino.rememberCupertinoPickerState
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun WheelPicker(
    value: String?,
    values: ImmutableList<PropertyValue>,
    modifier: Modifier = Modifier,
    label: String? = null,
    isError: Boolean = false,
    enabled: Boolean = true,
    onValueChange: (String) -> Unit
) {
    val state = rememberCupertinoPickerState(infinite = false)
    LaunchedEffect(value) {
        val index = values.indexOfFirst { it.const == value }
        if (index != -1) {
            state.scrollToItem(index)
        }
    }
    LaunchedEffect(state.selectedItemIndex) {
        onValueChange(values[state.selectedItemIndex].const)
    }
    CupertinoWheelPicker(
        modifier = modifier.fillMaxWidth(),
        state = state,
        items = values.map { it.title },
        enabled = enabled,
        content = { CupertinoText(it) }
    )
}
