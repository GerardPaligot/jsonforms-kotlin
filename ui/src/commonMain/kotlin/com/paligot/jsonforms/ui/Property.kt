package com.paligot.jsonforms.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.paligot.jsonforms.kotlin.SchemaProvider
import com.paligot.jsonforms.kotlin.internal.ext.propertyKey
import com.paligot.jsonforms.kotlin.models.schema.ArrayProperty
import com.paligot.jsonforms.kotlin.models.schema.BooleanProperty
import com.paligot.jsonforms.kotlin.models.schema.NumberProperty
import com.paligot.jsonforms.kotlin.models.schema.ObjectProperty
import com.paligot.jsonforms.kotlin.models.schema.Property
import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import com.paligot.jsonforms.kotlin.models.uischema.Control

@Composable
internal fun Property(
    control: Control,
    schemaProvider: SchemaProvider,
    jsonFormState: JsonFormState,
    stringContent: @Composable() (RendererStringScope.(id: String) -> Unit),
    numberContent: @Composable() (RendererNumberScope.(id: String) -> Unit),
    booleanContent: @Composable() (RendererBooleanScope.(id: String) -> Unit)
) {
    when (schemaProvider.getPropertyByControl<Property>(control)) {
        is StringProperty -> StringProperty(
            control = control,
            schemaProvider = schemaProvider,
            jsonFormState = jsonFormState,
            content = stringContent
        )
        is BooleanProperty -> BooleanProperty(
            control = control,
            schemaProvider = schemaProvider,
            jsonFormState = jsonFormState,
            content = booleanContent
        )
        is NumberProperty -> NumberProperty(
            control = control,
            schemaProvider = schemaProvider,
            jsonFormState = jsonFormState,
            content = numberContent
        )
        is ObjectProperty -> error("Object property can't be specified in the layout")
        is ArrayProperty -> TODO()
    }
}

@Composable
internal fun StringProperty(
    control: Control,
    schemaProvider: SchemaProvider,
    jsonFormState: JsonFormState,
    content: @Composable RendererStringScope.(id: String) -> Unit
) {
    val scope = remember(control) {
        RendererStringScopeInstance(control, schemaProvider, jsonFormState)
    }
    scope.content(control.propertyKey())
}

@Composable
internal fun BooleanProperty(
    control: Control,
    schemaProvider: SchemaProvider,
    jsonFormState: JsonFormState,
    content: @Composable RendererBooleanScope.(id: String) -> Unit
) {
    val scope = remember(control) {
        RendererBooleanScopeInstance(control, schemaProvider, jsonFormState)
    }
    scope.content(control.propertyKey())
}

@Composable
internal fun NumberProperty(
    control: Control,
    schemaProvider: SchemaProvider,
    jsonFormState: JsonFormState,
    content: @Composable RendererNumberScope.(id: String) -> Unit
) {
    val scope = remember(control) {
        RendererNumberScopeInstance(control, schemaProvider, jsonFormState)
    }
    scope.content(control.propertyKey())
}
