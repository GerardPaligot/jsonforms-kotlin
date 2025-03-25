package com.paligot.jsonforms.kotlin.models.uischema

import kotlinx.serialization.Serializable

/**
 * Represents a condition to be evaluated.
 */
@Serializable
data class Condition(
    /**
     * The scope that determines to which part this element should be bound to.
     */
    val scope: String,
    /**
     * Schema evaluated to apply or not the condition.
     */
    val schema: ConditionSchema
)
