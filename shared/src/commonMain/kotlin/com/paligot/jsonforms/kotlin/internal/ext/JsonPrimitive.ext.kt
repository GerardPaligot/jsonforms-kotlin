package com.paligot.jsonforms.kotlin.internal.ext

import kotlinx.serialization.json.JsonPrimitive

/**
 * Get any value from [JsonPrimitive] type which convert a string to [Double], [Int], [Float], [Boolean] or [String].
 */
val JsonPrimitive.anyValue: Any
    get() = when {
        this.content.toDoubleOrNull() != null -> this.content.toDouble()
        this.content.toIntOrNull() != null -> this.content.toInt()
        this.content.toFloatOrNull() != null -> this.content.toFloat()
        this.content.toBooleanStrictOrNull() != null -> this.content.toBoolean()
        else -> this.content
    }

/**
 * Get value from [JsonPrimitive] type which convert a string to [Double], [Int], [Float], [Boolean] or [String].
 */
inline fun <reified T> JsonPrimitive.value(): T = when (T::class) {
    Double::class -> this.content.toDoubleOrNull() as? T
        ?: throw IllegalArgumentException("Cannot convert to Double")
    Int::class -> this.content.toIntOrNull() as? T
        ?: throw IllegalArgumentException("Cannot convert to Int")
    Float::class -> this.content.toFloatOrNull() as? T
        ?: throw IllegalArgumentException("Cannot convert to Float")
    Boolean::class -> this.content.toBooleanStrictOrNull() as? T
        ?: throw IllegalArgumentException("Cannot convert to Boolean")
    String::class -> this.content as T
    else -> throw IllegalArgumentException("Unsupported type: ${T::class}")
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
