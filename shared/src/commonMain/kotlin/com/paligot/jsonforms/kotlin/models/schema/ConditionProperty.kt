package com.paligot.jsonforms.kotlin.models.schema

import com.paligot.jsonforms.kotlin.models.uischema.ConditionSchema
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.serialization.Serializable

@Serializable
data class ConditionProperty(
    val contains: Property? = null,
    val properties: ImmutableMap<String, ConditionSchema> = persistentMapOf(),
    val required: ImmutableList<String> = persistentListOf()
)
