package com.paligot.jsonforms.kotlin.models.uischema

import kotlinx.serialization.Serializable

/**
 * A rule that may be attached to any UI schema element.
 */
@Serializable
data class Rule(
    /**
     * The effect of the rule.
     */
    val effect: Effect,
    /**
     * The condition of the rule that must evaluate to true in order to trigger the effect.
     */
    val condition: Condition
)
