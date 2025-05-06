package com.paligot.jsonforms.kotlin.models.schema

import com.paligot.jsonforms.kotlin.models.serializers.ImmutableListSerializer
import com.paligot.jsonforms.kotlin.models.serializers.RegexSerializer
import kotlinx.collections.immutable.ImmutableList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonPrimitive

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
    override val const: JsonPrimitive? = null,
    override val not: Property? = null,
    @Serializable(with = RegexSerializer::class)
    override val pattern: Regex? = null,
    /**
     * An optional minimum length to validate the string value.
     */
    val minLength: Int? = null,
    /**
     * An optional maximum length to validate the string value.
     */
    val maxLength: Int? = null,
    /**
     * An optional enum value to restrict the value of the property.
     */
    @Serializable(with = ImmutableListSerializer::class)
    val enum: ImmutableList<String>? = null,
    /**
     * An optional list of key-value to validate the string value.
     */
    @Serializable(with = ImmutableListSerializer::class)
    val oneOf: ImmutableList<Property>? = null,
) : Property()
