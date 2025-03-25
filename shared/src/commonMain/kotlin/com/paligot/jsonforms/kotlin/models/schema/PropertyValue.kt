package com.paligot.jsonforms.kotlin.models.schema

import kotlinx.serialization.Serializable

/**
 * Key-value property.
 */
@Serializable
data class PropertyValue(
    /**
     * Value of the property.
     */
    val const: String,
    /**
     * Title or key of the property.
     */
    val title: String
)
