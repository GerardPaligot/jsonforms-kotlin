package com.paligot.jsonforms.kotlin.models.schema

import com.paligot.jsonforms.kotlin.internal.FieldError
import com.paligot.jsonforms.kotlin.models.PropertyValidation
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A property which configure a field with a number value.
 */
@Serializable
@SerialName("number")
data class NumberProperty(
    override val title: String? = null,
    override val format: String? = null,
    override val description: String? = null,
    override val readOnly: Boolean? = null,
    /**
     * An optional maximum to validate the number value.
     */
    val maximum: Int? = null,
    /**
     * An optional minimum to validate the number value.
     */
    val minimum: Int? = null,
    /**
     * An optional default value.
     */
    val default: Int? = null
) : Property(), PropertyValidation<String> {
    override fun validate(id: String, value: String): FieldError? {
        val floatValue = try {
            value.replace(",", ".").toFloat()
        } catch (exception: NumberFormatException) {
            return FieldError.MalformedFieldError(id)
        }
        if (minimum != null && floatValue < minimum) {
            return FieldError.MinValueFieldError(minimum, id)
        }
        if (maximum != null && floatValue > maximum) {
            return FieldError.MaxValueFieldError(maximum, id)
        }
        return null
    }
}
