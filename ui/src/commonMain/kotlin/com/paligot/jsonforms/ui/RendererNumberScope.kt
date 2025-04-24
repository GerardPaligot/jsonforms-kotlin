package com.paligot.jsonforms.ui

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import com.paligot.jsonforms.kotlin.SchemaProvider
import com.paligot.jsonforms.kotlin.internal.ext.isEnabled
import com.paligot.jsonforms.kotlin.internal.ext.label
import com.paligot.jsonforms.kotlin.models.schema.NumberProperty
import com.paligot.jsonforms.kotlin.models.uischema.Control

@Stable
interface RendererNumberScope {
    fun label(): String?

    fun description(): String?

    fun enabled(): Boolean

    fun keyboardOptions(
        capitalization: KeyboardCapitalization = KeyboardCapitalization.Unspecified,
        imeAction: ImeAction = ImeAction.Unspecified,
    ): KeyboardOptions
}

internal class RendererNumberScopeInstance(
    private val control: Control,
    private val schemaProvider: SchemaProvider,
    private val jsonFormState: JsonFormState,
    private val property: NumberProperty = schemaProvider.getPropertyByControl(control),
) : RendererNumberScope {
    override fun label(): String? =
        property
            .label(
                required = schemaProvider.propertyIsRequired(control, jsonFormState.getData()),
                control = control,
            )

    override fun description(): String? = property.description

    override fun enabled(): Boolean = property.isEnabled(control, jsonFormState.getData())

    override fun keyboardOptions(
        capitalization: KeyboardCapitalization,
        imeAction: ImeAction,
    ): KeyboardOptions =
        KeyboardOptions(
            capitalization = capitalization,
            keyboardType = KeyboardType.Number,
            imeAction = imeAction,
        )
}
