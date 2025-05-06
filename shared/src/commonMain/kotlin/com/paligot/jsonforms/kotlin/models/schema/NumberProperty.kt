package com.paligot.jsonforms.kotlin.models.schema

import com.paligot.jsonforms.kotlin.models.serializers.RegexSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonPrimitive

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
    override val const: JsonPrimitive? = null,
    override val not: Property? = null,
    @Serializable(with = RegexSerializer::class)
    override val pattern: Regex? = null,
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
    val default: Int? = null,
) : Property()
