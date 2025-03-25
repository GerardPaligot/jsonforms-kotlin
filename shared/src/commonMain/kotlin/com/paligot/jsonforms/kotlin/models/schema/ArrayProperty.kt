package com.paligot.jsonforms.kotlin.models.schema

import com.paligot.jsonforms.kotlin.models.serializers.ObjectPropertyListSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A property which configure a field with an array value.
 */
@Serializable
@SerialName("array")
data class ArrayProperty(
    override val title: String? = null,
    override val format: String? = null,
    override val description: String? = null,
    override val readOnly: Boolean? = null,
    val items: Property? = null,
    @Serializable(with = ObjectPropertyListSerializer::class)
    val prefixItems: List<ObjectProperty>? = null,
    val uniqueItems: Boolean = false,
    val allOf: List<ConditionProperty>? = null,
    @Serializable(with = ObjectPropertyListSerializer::class)
    val contains: List<Property>? = null
) : Property()
