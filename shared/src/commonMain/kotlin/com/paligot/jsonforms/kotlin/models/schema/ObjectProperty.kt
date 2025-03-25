package com.paligot.jsonforms.kotlin.models.schema

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
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
    val properties: ImmutableMap<String, Property>,
    /**
     * An optional list of required properties.
     */
    val required: ImmutableList<String> = persistentListOf(),
    override val title: String? = null,
    override val format: String? = null,
    override val description: String? = null,
    override val readOnly: Boolean? = null,
    val anyOf: ImmutableList<ConditionProperty>? = null,
    val oneOf: ImmutableList<ConditionProperty>? = null,
    val allOf: ImmutableList<ConditionProperty>? = null
) : Property()
