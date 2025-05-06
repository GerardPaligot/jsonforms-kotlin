package com.paligot.jsonforms.kotlin.internal.checks

import com.paligot.jsonforms.kotlin.internal.FieldError
import com.paligot.jsonforms.kotlin.internal.ext.anyValue
import com.paligot.jsonforms.kotlin.internal.ext.toJsonPrimitive
import com.paligot.jsonforms.kotlin.models.schema.Property

/**
 * Validates the given value against the constraints defined in the `Property`.
 *
 * This function checks various constraints such as `const`, `pattern`, and `not`
 * defined in the `Property` and returns a list of validation errors if the value
 * does not meet the constraints. If the value is valid, an empty list is returned.
 *
 * @param scopeKey The scope key of the property being validated.
 * @param value The value to validate.
 * @return A list of [FieldError] objects representing validation errors, or an empty list if the value is valid.
 */
internal fun Property.validateProperty(
    scopeKey: String,
    value: Any,
): List<FieldError> {
    val errors = mutableListOf<FieldError>()
    if (this.const != null) {
        if (this.const!!.anyValue != value.toJsonPrimitive()?.anyValue) {
            errors.add(FieldError.InvalidValueFieldError(value.toJsonPrimitive()!!, scopeKey))
        }
    }

    if (pattern != null) {
        if (!pattern!!.matches(value.toString())) {
            errors.add(FieldError.PatternFieldError(pattern!!.pattern, scopeKey))
        }
    }

    if (not != null) {
        val notValue = not!!.validateProperty(scopeKey, value)
        if (notValue.isEmpty()) {
            errors.add(FieldError.InvalidNotPropertyError(not!!, scopeKey))
        }
    }

    return errors
}
