package com.paligot.jsonforms.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.paligot.jsonforms.kotlin.internal.ext.evaluateHidden
import com.paligot.jsonforms.kotlin.models.uischema.Control
import com.paligot.jsonforms.kotlin.models.uischema.GroupLayout
import com.paligot.jsonforms.kotlin.models.uischema.HorizontalLayout
import com.paligot.jsonforms.kotlin.models.uischema.UiSchema
import com.paligot.jsonforms.kotlin.models.uischema.VerticalLayout

@Composable
fun Layout(
    uiSchema: UiSchema,
    jsonFormState: JsonFormState,
    layoutContent: @Composable (RendererLayoutScope.(@Composable (UiSchema) -> Unit) -> Unit),
    content: @Composable (Control) -> Unit
) {
    val hidden = uiSchema.rule?.evaluateHidden(jsonFormState.getData()) ?: false
    AnimatedVisibility(visible = !hidden) {
        when (uiSchema) {
            is VerticalLayout, is HorizontalLayout, is GroupLayout -> {
                val scope = remember(uiSchema) { RendererLayoutScopeInstance(uiSchema) }
                scope.layoutContent { child ->
                    Layout(
                        uiSchema = child,
                        jsonFormState = jsonFormState,
                        layoutContent = layoutContent,
                        content = content
                    )
                }
            }

            is Control -> content.invoke(uiSchema)
            else -> TODO("Unsupported layout")
        }
    }
}
