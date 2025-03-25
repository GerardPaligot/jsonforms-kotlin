package com.paligot.jsonforms.kotlin.internal.queries

import com.paligot.jsonforms.kotlin.internal.ControlLayout
import com.paligot.jsonforms.kotlin.internal.FieldError
import com.paligot.jsonforms.kotlin.internal.ext.propertyPath
import com.paligot.jsonforms.kotlin.models.schema.BooleanProperty
import com.paligot.jsonforms.kotlin.models.schema.NumberProperty
import com.paligot.jsonforms.kotlin.models.schema.ObjectProperty
import com.paligot.jsonforms.kotlin.models.schema.Property
import com.paligot.jsonforms.kotlin.models.schema.Schema
import com.paligot.jsonforms.kotlin.models.schema.StringProperty

/**
 * Check if all required fields have a value in the data map.
 *
 * ```kotlin
 * val schema = Schema(
 *     properties = mutableMapOf("key" to StringProperty()),
 *     required = listOf("key")
 * )
 * val uiControls = mapOf(
 *     "key" to ControlLayout(
 *         control = Control(scope = "#/properties/key"),
 *         layout = VerticalLayout(elements = mutableListOf(Control(scope = "#/properties/key")))
 *     )
 * )
 * val data = mapOf<String, Any?>("key" to "Value")
 * val required = data.checkRequired(schema = schema, uiControls = uiControls)
 * ```
 *
 * @param schema Properties which can be shown on the screen.
 * @param uiControls UI elements without the layout.
 * @param data a [Map] of data to evaluate anyOf, oneOf statements
 * @return required errors list
 */
fun Map<String, Any?>.checkRequired(
    schema: Schema,
    uiControls: Map<String, ControlLayout?>,
    data: Map<String, Any?>
): List<FieldError> {
    val properties = uiControls
        .filter { it.value != null }
        .filter { schema.propertyIsRequired(control = it.value!!.control, data = data) }
        .map { it.key }
    val errors = mutableListOf<FieldError>()
    for (it in (keys + properties)) {
        val uiControl = uiControls[it] ?: error("$it isn't in your UiSchema")
        if (uiControl.evaluateHidden(this)) {
            continue
        }
        if (schema.propertyIsRequired(control = uiControl.control, data = data).not()) {
            continue
        }
        val value = this[it]
        if (value == null) {
            errors.add(FieldError.RequiredFieldError(it))
        } else if (value is String && value == "") {
            errors.add(FieldError.RequiredFieldError(it))
        } else if (value is Boolean && value.not()) {
            errors.add(FieldError.RequiredFieldError(it))
        }
    }
    return errors
}

/**
 * Check if pattern are valid for all fields in the data map.
 *
 * ```kotlin
 * val schema = Schema(
 *     properties = mutableMapOf("key" to StringProperty(pattern = "^[0-9]{0,2}")),
 *     required = listOf("key")
 * )
 * val uiControls = mapOf(
 *     "key" to ControlLayout(
 *         control = Control(scope = "#/properties/key"),
 *         layout = VerticalLayout(elements = mutableListOf(Control(scope = "#/properties/key")))
 *     )
 * )
 * val data = mapOf<String, Any?>("key" to "01")
 * val patterns = data.checkPatterns(schema = schema, uiControls = uiControls)
 * ```
 *
 * @param schema Properties which can be shown on the screen.
 * @param uiControls UI elements without the layout.
 * @return pattern errors list
 */
fun Map<String, Any?>.checkPatterns(
    schema: Schema,
    uiControls: Map<String, ControlLayout?>
): List<FieldError> {
    val errors = mutableListOf<FieldError>()
    for (it in keys) {
        val uiControl = uiControls[it] ?: error("$it isn't in your UiSchema")
        val isHidden = uiControl.evaluateHidden(this)
        val value = this.getValue(it)
        if (isHidden || value == null || value == "") {
            continue
        }
        val error = propertyValidation(schema, uiControl.control.propertyPath(), value)
        if (error != null) {
            errors.add(error)
        }
    }
    return errors
}

private fun propertyValidation(schema: Schema, path: Array<String>, data: Any): FieldError? {
    var property: Property? = null
    val lastKey = path.last()
    path.forEachIndexed { index, key ->
        if (index == 0) {
            property = schema.properties[key]
        }
        (property as? ObjectProperty)?.properties?.get(key)?.let {
            property = it
        }
    }
    return when (val itControl = property) {
        is StringProperty -> itControl.validate(lastKey, data)
        is NumberProperty -> itControl.validate(lastKey, data as String)
        is BooleanProperty -> itControl.validate(lastKey, data as Boolean)
        else -> TODO()
    }
}
