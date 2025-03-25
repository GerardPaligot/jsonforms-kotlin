package com.paligot.jsonforms.kotlin.models.uischema

import kotlinx.collections.immutable.ImmutableList
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonPrimitive

/**
 * Schema of a condition to be evaluated.
 */
@Serializable
data class ConditionSchema(
    /**
     * Constant value.
     */
    val const: JsonPrimitive? = null,
    /**
     * List of values.
     */
    val enum: ImmutableList<String>? = null,
    /**
     * Negative constant value.
     */
    val not: ConditionSchema? = null,
    /**
     * An optional pattern to apply
     */
    val pattern: String? = null
)

/**
 * Get value from [JsonPrimitive] type which convert a string to [Double], [Int], [Float], [Boolean] or [String].
 */
val JsonPrimitive.value: Any
    get() = when {
        this.content.toDoubleOrNull() != null -> this.content.toDouble()
        this.content.toIntOrNull() != null -> this.content.toInt()
        this.content.toFloatOrNull() != null -> this.content.toFloat()
        this.content.toBooleanStrictOrNull() != null -> this.content.toBoolean()
        else -> this.content
    }

/**
 * Convert a generic value to a [JsonPrimitive] with a [String], [Number] or [Boolean].
 * If you try to convert a value with another type, a [NotImplementedError] exception will be thrown.
 * @return [JsonPrimitive] value
 */
fun Any?.toJsonPrimitive(): JsonPrimitive? {
    if (this == null) return null
    return when (this) {
        is String -> JsonPrimitive(this)
        is Number -> JsonPrimitive(this)
        is Boolean -> JsonPrimitive(this)
        else -> TODO("Is not implemented in JsonPrimitive")
    }
}
