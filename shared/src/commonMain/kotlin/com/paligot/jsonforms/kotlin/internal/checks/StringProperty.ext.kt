package com.paligot.jsonforms.kotlin.internal.checks

import com.paligot.jsonforms.kotlin.internal.FieldError
import com.paligot.jsonforms.kotlin.internal.ext.value
import com.paligot.jsonforms.kotlin.models.schema.StringProperty

/**
 * Validates the given string value against the constraints defined in the `StringProperty`.
 *
 * @param scopeKey The scope key of the property being validated.
 * @param value The string value to validate.
 * @return A list of [FieldError] objects representing validation errors, or an empty list if the value is valid.
 */
internal fun StringProperty.validate(scopeKey: String, value: String): List<FieldError> {
    val errors = mutableListOf<FieldError>()
    if (value.length < (minLength ?: 0)) {
        errors.add(FieldError.MinLengthFieldError(minLength ?: 0, scopeKey))
    }
    if (maxLength != null && value.length > maxLength) {
        errors.add(FieldError.MaxLengthFieldError(maxLength, scopeKey))
    }
    if (enum != null && !enum.contains(value)) {
        errors.add(FieldError.InvalidEnumFieldError(enum, scopeKey))
    }
    if (oneOf != null && oneOf.mapNotNull { it.const?.value<String>() }.contains(value)) {
        errors.add(
            FieldError.InvalidEnumFieldError(oneOf.mapNotNull { it.const?.content }, scopeKey)
        )
    }
    errors.addAll(this.validateProperty(scopeKey, value))
    return errors
}
