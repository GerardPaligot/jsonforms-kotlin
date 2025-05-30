package com.paligot.jsonforms.cupertino

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.paligot.jsonforms.cupertino.layout.Column
import com.paligot.jsonforms.cupertino.layout.CupertinoSection
import com.paligot.jsonforms.cupertino.layout.Row
import com.paligot.jsonforms.cupertino.ui.Checkbox
import com.paligot.jsonforms.cupertino.ui.OutlinedTextField
import com.paligot.jsonforms.cupertino.ui.SegmentedControl
import com.paligot.jsonforms.cupertino.ui.Switch
import com.paligot.jsonforms.cupertino.ui.WheelPicker
import com.paligot.jsonforms.kotlin.models.uischema.UiSchema
import com.paligot.jsonforms.ui.RendererBooleanScope
import com.paligot.jsonforms.ui.RendererLayoutScope
import com.paligot.jsonforms.ui.RendererNumberScope
import com.paligot.jsonforms.ui.RendererStringScope
import com.slapps.cupertino.ExperimentalCupertinoApi
import com.slapps.cupertino.section.SectionItem

@Composable
fun RendererStringScope.CupertinoStringProperty(
    value: String?,
    modifier: Modifier = Modifier,
    error: String? = null,
    onValueChange: (String) -> Unit,
) {
    when {
        isRadio() ->
            SegmentedControl(
                value = value,
                values = values(),
                modifier = modifier,
                label = label(),
                description = description(),
                error = error,
                onValueChange = onValueChange,
            )

        isDropdown() ->
            WheelPicker(
                value = value,
                values = values(),
                modifier = modifier,
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
fun RendererNumberScope.CupertinoNumberProperty(
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
fun RendererBooleanScope.CupertinoBooleanProperty(
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

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun RendererLayoutScope.CupertinoLayout(
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
                        content(child)
                    }
                },
            )

        isHorizontalLayout() ->
            Row(
                horizontalSpacing = horizontalSpacing(),
                modifier = modifier,
                content = {
                    for (child in elements()) {
                        content(child)
                    }
                },
            )

        isGroupLayout() ->
            CupertinoSection(
                title = title(),
                description = description(),
                modifier = modifier,
                content = {
                    for (child in elements()) {
                        SectionItem {
                            content(child)
                        }
                    }
                },
            )
    }
}
