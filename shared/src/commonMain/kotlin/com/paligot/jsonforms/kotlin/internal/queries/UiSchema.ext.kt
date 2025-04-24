package com.paligot.jsonforms.kotlin.internal.queries

import com.paligot.jsonforms.kotlin.internal.ext.evaluateShow
import com.paligot.jsonforms.kotlin.models.uischema.Control
import com.paligot.jsonforms.kotlin.models.uischema.GroupLayout
import com.paligot.jsonforms.kotlin.models.uischema.HorizontalLayout
import com.paligot.jsonforms.kotlin.models.uischema.UiSchema
import com.paligot.jsonforms.kotlin.models.uischema.VerticalLayout

/**
 * Finds all visible controls in the current [UiSchema] based on the provided data.
 *
 * This function traverses the [UiSchema] structure and evaluates visibility rules
 * for each element. If an element is visible, its associated control is included in the result.
 *
 * @param data A map containing the current field values of the form.
 * @return A list of controls corresponding to visible elements in the [UiSchema].
 */
internal fun UiSchema.findVisibleControls(data: Map<String, Any?>): List<Control> =
    when (this) {
        is GroupLayout, is HorizontalLayout, is VerticalLayout -> {
            elements?.flatMap { it.findVisibleControls(data) } ?: emptyList()
        }
        is Control -> {
            if (rule == null || rule?.evaluateShow(data) == true) {
                listOfNotNull(this)
            } else {
                emptyList()
            }
        }
    }
