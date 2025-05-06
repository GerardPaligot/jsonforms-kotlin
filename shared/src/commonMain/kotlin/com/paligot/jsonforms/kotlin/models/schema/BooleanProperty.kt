package com.paligot.jsonforms.kotlin.models.schema

import com.paligot.jsonforms.kotlin.models.serializers.RegexSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonPrimitive

/**
 * A property which configure a field with a boolean value.
 */
@Serializable
@SerialName("boolean")
data class BooleanProperty(
    override val title: String? = null,
    override val format: String? = null,
    override val description: String? = null,
    override val readOnly: Boolean? = null,
    override val const: JsonPrimitive? = null,
    override val not: Property? = null,
    @Serializable(with = RegexSerializer::class)
    override val pattern: Regex? = null,
) : Property()
