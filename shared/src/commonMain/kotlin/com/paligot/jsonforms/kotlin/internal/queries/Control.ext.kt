package com.paligot.jsonforms.kotlin.internal.queries

import com.paligot.jsonforms.kotlin.internal.ext.isDropdown
import com.paligot.jsonforms.kotlin.models.schema.Property
import com.paligot.jsonforms.kotlin.models.schema.Schema
import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import com.paligot.jsonforms.kotlin.models.uischema.Control
import com.paligot.jsonforms.kotlin.models.uischema.UiSchema

/**
 * Check if the [Control] is the last field declared in the [UiSchema].
 *
 * ```kotlin
 * val first = Control(scope = "#/properties/key")
 * val last = Control(scope = "#/properties/key2")
 * val uiSchema = VerticalLayout(elements = mutableListOf(first, last))
 * val schema = Schema(
 *     properties = mutableMapOf(
 *         "key" to StringProperty(),
 *         "key2" to StringProperty()
 *     )
 * )
 * val isLastProperty = first.isLastField(uiSchema, schema) // false
 * val isLastProperty = last.isLastField(uiSchema, schema) // true
 * ```
 *
 * @param uiSchema Form UI description of fields declared in [Schema].
 * @param schema Properties which can be shown on the screen.
 * @return true if the control is the last field
 */
internal fun Control.isLastField(
    uiSchema: UiSchema,
    schema: Schema,
): Boolean = lastField(this, uiSchema, schema)

private fun lastField(
    control: Control,
    parent: UiSchema,
    schema: Schema,
): Boolean {
    val sizeElements = parent.elements?.size ?: 0
    if (sizeElements == 0) {
        return false
    }
    val lastIndex =
        parent.elements?.indexOfLast { uiSchema ->
            when (uiSchema) {
                is Control -> uiSchema.scope == control.scope
                else -> false
            }
        }
    // If the control isn't in the list, we inspect the last element to check if it contains sub fields
    if (lastIndex == null || lastIndex == -1) {
        val lastElement = parent.elements?.last()
        return if (lastElement != null) {
            lastField(control, lastElement, schema)
        } else {
            false
        }
    }
    // If the control is the last element, we found it
    if (lastIndex == sizeElements - 1) {
        return true
    }
    // Otherwise, we check if elements between our control and the last one contains a string
    // element. It is necessary to check because we only need a keyboard for string properties.
    // So, if they aren't any string properties between our control and the last one, we can
    // consider that our current control is the last field which requires a keyboard.
    for (i in (lastIndex + 1) until sizeElements) {
        val element = parent.elements?.get(i) ?: error("Element $i not found")
        if (element !is Control) {
            continue
        }
        val property = schema.getPropertyByControl<Property>(element)
        if (property is StringProperty) {
            val readOnly = property.readOnly ?: false
            val isDropdown = property.isDropdown()
            if (readOnly || isDropdown) continue
            return false
        }
    }
    return true
}
