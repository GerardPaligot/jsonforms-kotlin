package com.paligot.jsonforms.material3

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.paligot.jsonforms.kotlin.models.uischema.UiSchema
import com.paligot.jsonforms.material3.layout.Column
import com.paligot.jsonforms.material3.layout.Row
import com.paligot.jsonforms.material3.ui.Checkbox
import com.paligot.jsonforms.material3.ui.OutlinedDropdown
import com.paligot.jsonforms.material3.ui.OutlinedTextField
import com.paligot.jsonforms.material3.ui.RadioButton
import com.paligot.jsonforms.material3.ui.Switch
import com.paligot.jsonforms.ui.RendererBooleanScope
import com.paligot.jsonforms.ui.RendererLayoutScope
import com.paligot.jsonforms.ui.RendererNumberScope
import com.paligot.jsonforms.ui.RendererStringScope

@Composable
fun RendererStringScope.Material3StringProperty(
    value: String?,
    modifier: Modifier = Modifier,
    error: String? = null,
    onValueChange: (String) -> Unit,
) {
    when {
        isRadio() ->
            RadioButton(
                value = value,
                values = values(),
                orientation = orientation(),
                modifier = modifier,
                label = label(),
                description = description(),
                error = error,
                enabled = enabled(),
                verticalArrangement = verticalArrangement(),
                horizontalArrangement = horizontalArrangement(),
                onValueChange = onValueChange,
            )

        isDropdown() ->
            OutlinedDropdown(
                value = value,
                values = values(),
                modifier = modifier,
                label = label(),
                isError = error != null,
                enabled = enabled(),
                onValueChange = onValueChange,
            )

        else ->
            OutlinedTextField(
                value = value,
                modifier = modifier,
                label = label(),
                description = description(),
                enabled = enabled(),
                error = error,
                visualTransformation = visualTransformation(),
                keyboardOptions = keyboardOptions(),
                onValueChange = onValueChange,
            )
    }
}

@Composable
fun RendererNumberScope.Material3NumberProperty(
    value: String?,
    modifier: Modifier = Modifier,
    error: String? = null,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        modifier = modifier,
        label = label(),
        description = description(),
        enabled = enabled(),
        error = error,
        keyboardOptions = keyboardOptions(),
        onValueChange = onValueChange,
    )
}

@Composable
fun RendererBooleanScope.Material3BooleanProperty(
    value: Boolean,
    modifier: Modifier = Modifier,
    onValueChange: (Boolean) -> Unit,
) {
    when {
        isToggle() ->
            Switch(
                value = value,
                modifier = modifier,
                label = label(),
                description = description(),
                enabled = enabled(),
                onCheckedChange = onValueChange,
            )

        else ->
            Checkbox(
                value = value,
                modifier = modifier,
                label = label(),
                enabled = enabled(),
                onCheckedChange = onValueChange,
            )
    }
}

@Composable
fun RendererLayoutScope.Material3Layout(
    modifier: Modifier = Modifier,
    content: @Composable (UiSchema) -> Unit,
) {
    when {
        isVerticalLayout() ->
            Column(
                verticalSpacing = verticalSpacing(),
                modifier = modifier,
                content = {
                    for (child in elements()) {
                        val childModifier =
                            child.options?.weight
                                ?.let { Modifier.weight(it) }
                                ?: Modifier
                        Box(modifier = childModifier) {
                            content(child)
                        }
                    }
                },
            )

        isHorizontalLayout() ->
            Row(
                horizontalSpacing = horizontalSpacing(),
                modifier = modifier,
                content = {
                    for (child in elements()) {
                        val childModifier =
                            child.options?.weight
                                ?.let { Modifier.weight(it) }
                                ?: Modifier
                        Box(modifier = childModifier) {
                            content(child)
                        }
                    }
                },
            )

        isGroupLayout() -> TODO()
    }
}
