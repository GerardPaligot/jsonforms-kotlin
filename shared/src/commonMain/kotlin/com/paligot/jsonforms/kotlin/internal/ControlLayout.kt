package com.paligot.jsonforms.kotlin.internal

import com.paligot.jsonforms.kotlin.internal.ext.evaluateHidden
import com.paligot.jsonforms.kotlin.models.uischema.Control
import com.paligot.jsonforms.kotlin.models.uischema.UiSchema

/**
 * [Control] component with its [UiSchema] parent.
 * This class is used by finder classes to define what is the parent layout and if
 * a rule should be applied on the control.
 */
data class ControlLayout(
    val control: Control,
    val layout: UiSchema
) {
    /**
     * Evaluate [control] and [layout] fields to check if the [control] should be hidden or not.
     *
     * @param data field values of the form
     * @return true if the [control] should be hidden, otherwise false.
     */
    fun evaluateHidden(data: Map<String, Any?>): Boolean =
        control.rule?.evaluateHidden(data) ?: false || layout.rule?.evaluateHidden(data) ?: false
}
