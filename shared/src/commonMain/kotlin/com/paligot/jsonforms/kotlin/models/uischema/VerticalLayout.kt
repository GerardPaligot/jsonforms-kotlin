package com.paligot.jsonforms.kotlin.models.uischema

import com.paligot.jsonforms.kotlin.models.serializers.ImmutableListSerializer
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A layout which orders its child elements vertically (i.e. from top to bottom).
 */
@Serializable
@SerialName("VerticalLayout")
data class VerticalLayout(
    @Serializable(with = ImmutableListSerializer::class)
    override val elements: ImmutableList<UiSchema> = persistentListOf(),
    override val rule: Rule? = null,
    override val options: LayoutOptions? = null
) : UiSchema()
