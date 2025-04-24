package com.paligot.jsonforms.kotlin.internal.ext

import com.paligot.jsonforms.kotlin.internal.checks.validate
import com.paligot.jsonforms.kotlin.models.schema.ArrayProperty
import com.paligot.jsonforms.kotlin.models.schema.BooleanProperty
import com.paligot.jsonforms.kotlin.models.schema.NumberProperty
import com.paligot.jsonforms.kotlin.models.schema.ObjectProperty
import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import com.paligot.jsonforms.kotlin.models.uischema.Effect
import com.paligot.jsonforms.kotlin.models.uischema.Rule

/**
 * Evaluate the rule to check if we should show the field.
 *
 * ```kotlin
 * val rule = Rule(
 *     effect = Effect.Show,
 *     condition = Condition(
 *         scope = "#/properties/key",
 *         schema = ConditionSchema(const = JsonPrimitive(""))
 *     )
 * )
 * val hidden = rule.evaluateShow(mapOf("key" to ""))
 * ```
 *
 * @param data field values of the form.
 * @return true if the condition of the rule is evaluated to true.
 */
fun Rule.evaluateShow(data: Map<String, Any?>): Boolean {
    val key = condition.propertyKey()
    val value = data[key]
    val errors =
        when (condition.schema) {
            is StringProperty -> condition.schema.validate(key, value as? String ?: "")
            is BooleanProperty -> condition.schema.validate(key, value as? Boolean ?: false)
            is NumberProperty -> condition.schema.validate(key, value as? String ?: "")
            is ObjectProperty -> condition.schema.validate(data)
            is ArrayProperty -> TODO()
        }
    return if (effect == Effect.Show && errors.isEmpty()) {
        true
    } else if (effect == Effect.Show && errors.isNotEmpty()) {
        false
    } else if (effect == Effect.Hide && errors.isEmpty()) {
        false
    } else if (effect == Effect.Hide && errors.isNotEmpty()) {
        true
    } else {
        false
    }
}

/**
 * Evaluate the rule to check if we should disable the field.
 *
 * ```kotlin
 * val rule = Rule(
 *     effect = Effect.Enable,
 *     condition = Condition(
 *         scope = "#/properties/key",
 *         schema = ConditionSchema(const = JsonPrimitive(""))
 *     )
 * )
 * val hidden = rule.evaluateEnabled(mapOf("key" to ""))
 * ```
 *
 * @param data field values of the form.
 * @return true if the condition of the rule is evaluated to true.
 */
fun Rule.evaluateEnabled(data: Map<String, Any?>): Boolean {
    val key = condition.propertyKey()
    val value = data[key]
    val resolve =
        when (condition.schema) {
            is StringProperty -> condition.schema.validate(key, value as? String ?: "")
            is BooleanProperty -> condition.schema.validate(key, value as? Boolean ?: false)
            is NumberProperty -> condition.schema.validate(key, value as? String ?: "")
            is ObjectProperty -> condition.schema.validate(data)
            is ArrayProperty -> TODO()
        }
    if (effect == Effect.Enable && resolve.isEmpty()) return true
    if (effect == Effect.Enable && resolve.isNotEmpty()) return false
    if (effect == Effect.Disable && resolve.isEmpty()) return false
    if (effect == Effect.Disable && resolve.isNotEmpty()) return true
    return true
}
