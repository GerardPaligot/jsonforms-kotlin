package com.paligot.jsonforms.kotlin.models.schema

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonPrimitive

/**
 * Common base sealed class for any property element.
 */
@Serializable
sealed class Property {
    /**
     * An optional title.
     */
    abstract val title: String?

    /**
     * An optional format.
     */
    abstract val format: String?

    /**
     * An optional description.
     */
    abstract val description: String?

    /**
     * An optional boolean to know if it is interactive.
     */
    abstract val readOnly: Boolean?

    /**
     * An optional const value to restrict the value of the property.
     */
    abstract val const: JsonPrimitive?

    /**
     * An optional not value to restrict the value of the property.
     */
    abstract val not: Property?

    /**
     * An optional pattern to restrict the value of the property.
     */
    abstract val pattern: String?
}
