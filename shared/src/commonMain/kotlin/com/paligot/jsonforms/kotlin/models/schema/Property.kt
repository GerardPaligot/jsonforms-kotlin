package com.paligot.jsonforms.kotlin.models.schema

import kotlinx.serialization.Serializable

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
}
