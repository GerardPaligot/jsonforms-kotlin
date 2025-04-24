package com.paligot.jsonforms.kotlin.models.uischema

import com.paligot.jsonforms.kotlin.models.serializers.ImmutableListSerializer
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A group resembles a vertical layout, but additionally might have a label.
 * This layout is useful when grouping different elements by a certain criteria.
 */
@Serializable
@SerialName("Group")
data class GroupLayout(
    /**
     * Label for UI schema element
     */
    val label: String,
    val description: String? = null,
    @Serializable(with = ImmutableListSerializer::class)
    override val elements: ImmutableList<UiSchema> = persistentListOf(),
    override val rule: Rule? = null,
    override val options: LayoutOptions? = null,
) : UiSchema()
