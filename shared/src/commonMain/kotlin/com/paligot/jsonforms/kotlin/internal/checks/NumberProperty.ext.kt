package com.paligot.jsonforms.kotlin.internal.checks

import com.paligot.jsonforms.kotlin.internal.FieldError
import com.paligot.jsonforms.kotlin.models.schema.NumberProperty

/**
 * Validates a numeric property against its constraints such as minimum, maximum, and format.
 *
 * @param scopeKey The scope key of the property being validated.
 * @param value The string representation of the numeric value to validate.
 * @return A list of [FieldError] containing validation errors, if any.
 */
internal fun NumberProperty.validate(
    scopeKey: String,
    value: String,
): List<FieldError> {
    val errors = mutableListOf<FieldError>()
    val floatValue =
        try {
            value.replace(",", ".").toFloat()
        } catch (exception: NumberFormatException) {
            errors.add(FieldError.MalformedFieldError(scopeKey))
            return errors
        }
    if (minimum != null && floatValue < minimum) {
        errors.add(FieldError.MinValueFieldError(minimum, scopeKey))
    }
    if (maximum != null && floatValue > maximum) {
        errors.add(FieldError.MaxValueFieldError(maximum, scopeKey))
    }
    errors.addAll(validateProperty(scopeKey, value))
    return errors
}
