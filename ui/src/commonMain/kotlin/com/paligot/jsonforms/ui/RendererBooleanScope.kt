package com.paligot.jsonforms.ui

import androidx.compose.runtime.Stable
import com.paligot.jsonforms.kotlin.SchemaProvider
import com.paligot.jsonforms.kotlin.internal.ext.isEnabled
import com.paligot.jsonforms.kotlin.internal.ext.isToggle
import com.paligot.jsonforms.kotlin.internal.ext.label
import com.paligot.jsonforms.kotlin.models.schema.BooleanProperty
import com.paligot.jsonforms.kotlin.models.uischema.Control

@Stable
interface RendererBooleanScope {
    fun isToggle(): Boolean
    fun label(): String?
    fun description(): String?
    fun enabled(): Boolean
}

internal class RendererBooleanScopeInstance(
    private val control: Control,
    private val schemaProvider: SchemaProvider,
    private val jsonFormState: JsonFormState,
    private val property: BooleanProperty = schemaProvider.getPropertyByControl(control)
) : RendererBooleanScope {
    override fun isToggle(): Boolean = property.isToggle(control)

    override fun label(): String? = property
        .label(
            required = schemaProvider.propertyIsRequired(control, jsonFormState.getData()),
            control = control
        )

    override fun description(): String? = property.description

    override fun enabled(): Boolean = property.isEnabled(control, jsonFormState.getData())
}
