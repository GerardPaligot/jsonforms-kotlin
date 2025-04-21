package com.paligot.jsonforms.kotlin.internal.checks

import com.paligot.jsonforms.kotlin.internal.FieldError
import com.paligot.jsonforms.kotlin.internal.ext.propertyKey
import com.paligot.jsonforms.kotlin.internal.queries.findVisibleControls
import com.paligot.jsonforms.kotlin.models.schema.Schema
import com.paligot.jsonforms.kotlin.models.uischema.UiSchema

class ValidationCheck(
    private val schema: Schema,
    private val uiSchema: UiSchema
) {
    fun check(data: Map<String, Any?>): List<FieldError> {
        val controls = uiSchema.findVisibleControls(data)
        return schema.validate(data, controls.map { it.propertyKey() })
    }
}
