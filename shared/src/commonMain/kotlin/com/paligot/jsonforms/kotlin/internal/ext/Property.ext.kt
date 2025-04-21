package com.paligot.jsonforms.kotlin.internal.ext

import com.paligot.jsonforms.kotlin.models.schema.NumberProperty
import com.paligot.jsonforms.kotlin.models.schema.Property
import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import com.paligot.jsonforms.kotlin.models.uischema.Control

/**
 * Check if the property is enabled from the [control] rule if it exists.
 * The data property is necessary because rules are based on a condition in relation to another field.
 *
 * ```kotlin
 * val property = StringProperty()
 * val control = Control(scope = "#/properties/string")
 * val enabled = property.isEnabled(control, emptyMap())
 * ```
 *
 * @param control Field contained in the [com.paligot.jsonforms.kotlin.models.uischema.UiSchema].
 * @param data field values of the form.
 * @return true if the property is enabled
 */
fun Property.isEnabled(control: Control, data: Map<String, Any?>): Boolean {
    val enabledRule = control.rule?.evaluateEnabled(data)
    if (enabledRule != null) return enabledRule
    return readOnly?.not() ?: control.options?.readOnly?.not() ?: true
}

/**
 * Get the label of a field from the [Property] or the [Control] with '*' if it is required.
 * If there is no title in the [Property] or label in the [Control], it'll return null as label.
 *
 * ```kotlin
 * val property = StringProperty(title = "My Label")
 * val control = Control(scope = "#/properties/label")
 * val label = property.label(required = true, control = control)
 * ```
 *
 * @param required Field is required or not
 * @param control Field contained in the [com.paligot.jsonforms.kotlin.models.uischema.UiSchema].
 * @return label with an '*' if the field is required, null if there is no label in [Control] or [Property]
 */
fun Property.label(required: Boolean, control: Control): String? {
    val suffix = if (required) "*" else ""
    return if (title != null) {
        "$title$suffix"
    } else if (control.label != null) {
        "${control.label}$suffix"
    } else {
        null
    }
}

/**
 * Return a Pair of Int (current value length to max length), if the property is a TextInputProperty, control options showMaxCounter is true and has a max length specified.
 *
 * ```kotlin
 * val property = StringProperty()
 * val control = Control(scope = "#/properties/string")
 * val counter = property.getMaxLengthCounter(value.length, control)
 * ```
 *
 * @param value Length of the current value, or 0 if value is null
 * @param control Field contained in the [com.paligot.jsonforms.kotlin.models.uischema.UiSchema].
 * @return Pair of Int (current value length to max length)
 */
fun Property.getMaxCounter(value: String?, control: Control): Pair<Int, Int>? {
    return if (
        this is StringProperty &&
        control.options?.showMaxCounter == true &&
        maxLength != null
    ) {
        Pair(value?.length ?: 0, maxLength)
    } else if (
        this is NumberProperty &&
        control.options?.showMaxCounter == true &&
        maximum != null
    ) {
        Pair(value.getIntegerValue(), maximum)
    } else {
        null
    }
}

private fun String?.getIntegerValue(): Int = try {
    // if the value if "" or just white space, we return 0
    if (isNullOrEmpty()) {
        0
    } else {
        // Like the validate method we call toFloat(). The counter want a Integer, so we transform the float to Integer, so 47.17 will be 47.
        replace(",", ".").toFloat().toInt()
    }
} catch (ex: Exception) {
    // In the case of value = 12-5 or 12,5, we can't parse a Float with these entries, so we show 0 to prevent a crash at the validation
    0
}
