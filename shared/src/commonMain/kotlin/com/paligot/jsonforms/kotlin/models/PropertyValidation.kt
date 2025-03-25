package com.paligot.jsonforms.kotlin.models

import com.paligot.jsonforms.kotlin.internal.FieldError

/**
 * Common interface to apply a static validation of a property.
 */
interface PropertyValidation<V> {
    /**
     * Validate the property.
     * @param id Property identifier
     * @param value Property value
     * @return error localized or null if there is no error.
     */
    fun validate(id: String, value: V): FieldError?
}
