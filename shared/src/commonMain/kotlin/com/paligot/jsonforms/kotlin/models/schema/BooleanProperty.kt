package com.paligot.jsonforms.kotlin.models.schema

import com.paligot.jsonforms.kotlin.internal.FieldError
import com.paligot.jsonforms.kotlin.models.PropertyValidation
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A property which configure a field with a boolean value.
 */
@Serializable
@SerialName("boolean")
data class BooleanProperty(
    override val title: String? = null,
    override val format: String? = null,
    override val description: String? = null,
    override val readOnly: Boolean? = null
) : Property(), PropertyValidation<Boolean> {
    override fun validate(id: String, value: Boolean): FieldError? = null
}
