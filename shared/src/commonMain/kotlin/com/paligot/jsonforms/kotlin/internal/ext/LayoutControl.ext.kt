package com.paligot.jsonforms.kotlin.internal.ext

import com.paligot.jsonforms.kotlin.models.uischema.Condition
import com.paligot.jsonforms.kotlin.models.uischema.Control

/**
 * Get key of the [Control] at the end of the path in the scope.
 *
 * ```kotlin
 * val control = Control(scope = "#/properties/key")
 * val key = control.propertyKey()
 * ```
 *
 * @return key of the [Control]
 */
fun Control.propertyKey(): String = scope.propertyKey()

/**
 * Get key of the [Condition] at the end of the path in the scope.
 *
 * ```kotlin
 * val condition = Condition(
 *     scope = "#/properties/key",
 *     schema = ConditionSchema(const = JsonPrimitive(""))
 * )
 * val key = condition.propertyKey()
 * ```
 *
 * @return key of the [Control]
 */
fun Condition.propertyKey(): String = scope.propertyKey()

/**
 * Split the property path from the scope property to an array of keys.
 * If your scope is `#/properties/key`, it will return ["key"]
 * but if your scope is `#/properties/key1/properties/key2`, it will return ["key1", "key2"]
 *
 * ```kotlin
 * val control = Control(scope = "#/properties/key1/properties/key2")
 * val keys = control.propertyPath()
 * ```
 *
 * @return list of keys
 */
fun Control.propertyPath(): Array<String> {
    if (!pathPattern.matches(scope)) error("Property $this malformed")
    val split = scope.split("/properties/")
    // Remove the first item which is '#' character.
    return split.slice(IntRange(1, split.size - 1)).toTypedArray()
}

private val pathPattern = Regex("^#(?:/properties/([a-zA-Z0-9_.-]+))+")

private fun String.propertyKey(): String {
    if (!this.matches(pathPattern)) error("Property $this malformed")
    return split("/").last()
}
