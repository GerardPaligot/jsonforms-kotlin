package com.paligot.jsonforms.kotlin.models.schema

import com.paligot.jsonforms.kotlin.internal.FieldError
import com.paligot.jsonforms.kotlin.models.PropertyValidation
import kotlinx.collections.immutable.ImmutableList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A property which configure a field with a string value.
 */
@Serializable
@SerialName("string")
data class StringProperty(
    override val title: String? = null,
    override val format: String? = null,
    override val description: String? = null,
    override val readOnly: Boolean? = null,
    /**
     * An optional pattern to validate the string value.
     */
    val pattern: String? = null,
    /**
     * An optional minimum length to validate the string value.
     */
    val minLength: Int? = null,
    /**
     * An optional maximum length to validate the string value.
     */
    val maxLength: Int? = null,

    /**
     * An optional list of values to validate the string value.
     */
    val enum: ImmutableList<String>? = null,
    /**
     * An optional list of key-value to validate the string value.
     */
    val oneOf: ImmutableList<PropertyValue>? = null
) : Property(), PropertyValidation<Any> {
    override fun validate(id: String, value: Any): FieldError? {
        if (value is String) {
            if (value.length < (minLength ?: 0)) {
                return FieldError.MinLengthFieldError(minLength ?: 0, id)
            }
            if (maxLength != null && value.length > maxLength) {
                return FieldError.MaxLengthFieldError(maxLength, id)
            }

            if (pattern != null) {
                val patternRegex = Regex(pattern)
                if (!patternRegex.matches(value)) {
                    return FieldError.PatternFieldError(pattern, id)
                }
            }
        } else if (value is Boolean) {
            return null
        }
        return null
    }
}
