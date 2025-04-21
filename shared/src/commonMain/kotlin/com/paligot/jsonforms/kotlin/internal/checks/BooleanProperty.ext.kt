package com.paligot.jsonforms.kotlin.internal.checks

import com.paligot.jsonforms.kotlin.internal.FieldError
import com.paligot.jsonforms.kotlin.models.schema.BooleanProperty

/**
 * Validates the given boolean value against the constraints defined in the `BooleanProperty`.
 *
 * @param scopeKey The scope key of the property being validated.
 * @param value The boolean value to validate.
 * @return A list of [FieldError] objects representing validation errors, or an empty list if the value is valid.
 */
internal fun BooleanProperty.validate(scopeKey: String, value: Boolean): List<FieldError> =
    validateProperty(scopeKey, value)
