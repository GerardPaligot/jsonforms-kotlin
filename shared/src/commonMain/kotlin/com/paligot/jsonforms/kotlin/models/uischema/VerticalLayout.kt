package com.paligot.jsonforms.kotlin.models.uischema

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A layout which orders its child elements vertically (i.e. from top to bottom).
 */
@Serializable
@SerialName("VerticalLayout")
class VerticalLayout(
    override val elements: ImmutableList<UiSchema> = persistentListOf(),
    override val rule: Rule? = null,
    override val options: LayoutOptions? = null
) : UiSchema()
