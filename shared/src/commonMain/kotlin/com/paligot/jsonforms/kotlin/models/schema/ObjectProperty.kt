package com.paligot.jsonforms.kotlin.models.schema

import com.paligot.jsonforms.kotlin.models.serializers.ImmutableListSerializer
import com.paligot.jsonforms.kotlin.models.serializers.ImmutableMapSerializer
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonPrimitive
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * A property which configure a field with an object value.
 */
@OptIn(ExperimentalObjCName::class)
@Serializable
@SerialName("object")
@ObjCName("Schema")
data class ObjectProperty(
    /**
     * The child properties of this property.
     */
    @Serializable(with = ImmutableMapSerializer::class)
    val properties: ImmutableMap<String, Property>,
    /**
     * An optional list of required properties.
     */
    @Serializable(with = ImmutableListSerializer::class)
    val required: ImmutableList<String> = persistentListOf(),
    /**
     * An optional list of properties that are required if any of the properties in the
     * `anyOf` array are present.
     */
    @Serializable(with = ImmutableListSerializer::class)
    val anyOf: ImmutableList<ObjectProperty>? = null,
    override val title: String? = null,
    override val format: String? = null,
    override val description: String? = null,
    override val readOnly: Boolean? = null,
    override val const: JsonPrimitive? = null,
    override val not: Property? = null,
    override val pattern: String? = null,
) : Property()
