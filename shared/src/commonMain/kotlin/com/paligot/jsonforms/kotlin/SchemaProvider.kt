package com.paligot.jsonforms.kotlin

import com.paligot.jsonforms.kotlin.internal.checks.propertyIsRequired
import com.paligot.jsonforms.kotlin.internal.queries.getPropertyByControl
import com.paligot.jsonforms.kotlin.internal.queries.isLastField
import com.paligot.jsonforms.kotlin.models.schema.Property
import com.paligot.jsonforms.kotlin.models.schema.Schema
import com.paligot.jsonforms.kotlin.models.uischema.Control
import com.paligot.jsonforms.kotlin.models.uischema.UiSchema

interface SchemaProvider {
    /**
     * Get schema property from the key contained in a [Control].
     */
    fun <T : Property> getPropertyByControl(control: Control): T

    /**
     * Check if the control is specified as required in schema.
     */
    fun propertyIsRequired(
        control: Control,
        data: Map<String, Any?>,
    ): Boolean

    /**
     * Check if the control is the last field in the ui-schema.
     */
    fun isLastField(control: Control): Boolean
}

class SchemaProviderImpl(private val uiSchema: UiSchema, private val schema: Schema) :
    SchemaProvider {
    override fun <T : Property> getPropertyByControl(control: Control): T = schema.getPropertyByControl(control)

    override fun propertyIsRequired(
        control: Control,
        data: Map<String, Any?>,
    ): Boolean = schema.propertyIsRequired(control, data)

    override fun isLastField(control: Control): Boolean = control.isLastField(uiSchema, schema)
}
