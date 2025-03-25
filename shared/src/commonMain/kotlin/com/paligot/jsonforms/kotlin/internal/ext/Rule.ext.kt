package com.paligot.jsonforms.kotlin.internal.ext

import com.paligot.jsonforms.kotlin.models.uischema.ConditionSchema
import com.paligot.jsonforms.kotlin.models.uischema.Effect
import com.paligot.jsonforms.kotlin.models.uischema.Rule
import com.paligot.jsonforms.kotlin.models.uischema.toJsonPrimitive
import com.paligot.jsonforms.kotlin.models.uischema.value
import kotlinx.serialization.json.JsonPrimitive

/**
 * Evaluate the rule to check if we should hide the field.
 *
 * ```kotlin
 * val rule = Rule(
 *     effect = Effect.Show,
 *     condition = Condition(
 *         scope = "#/properties/key",
 *         schema = ConditionSchema(const = JsonPrimitive(""))
 *     )
 * )
 * val hidden = rule.evaluateHidden(mapOf("key" to ""))
 * ```
 *
 * @param data field values of the form.
 * @return true if the condition of the rule is evaluated to true.
 */
fun Rule.evaluateHidden(data: Map<String, Any?>): Boolean {
    val key = condition.propertyKey()
    val resolve = condition.schema.resolve(key, data)
    return if (effect == Effect.Show && resolve) {
        false
    } else if (effect == Effect.Show && !resolve) {
        true
    } else if (effect == Effect.Hide && resolve) {
        true
    } else if (effect == Effect.Hide && !resolve) {
        false
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
    val resolve = condition.schema.resolve(key, data)
    if (effect == Effect.Enable && resolve) return true
    if (effect == Effect.Enable && !resolve) return false
    if (effect == Effect.Disable && resolve) return false
    if (effect == Effect.Disable && !resolve) return true
    return true
}

fun ConditionSchema.resolve(key: String, data: Map<String, Any?>): Boolean {
    if (const == null && enum == null && not == null && pattern == null) return false
    return (
        const.resolveConst(key, data) &&
            enum.resolveEnum(key, data) &&
            not.resolveNot(key, data) &&
            pattern.resolvePattern(key, data)
        )
}

private fun JsonPrimitive?.resolveConst(key: String, data: Map<String, Any?>): Boolean {
    if (this == null) return true
    return this.value == data[key].toJsonPrimitive()?.value
}

private fun List<String>?.resolveEnum(key: String, data: Map<String, Any?>): Boolean {
    if (this == null) return true
    if (this.isEmpty()) return false
    return this.contains(data[key])
}

private fun ConditionSchema?.resolveNot(key: String, data: Map<String, Any?>): Boolean {
    if (this == null) return true
    return this.resolve(key, data).not()
}

private fun String?.resolvePattern(key: String, data: Map<String, Any?>): Boolean {
    if (this == null) return true
    return Regex(this).matches(data[key].toString())
}
