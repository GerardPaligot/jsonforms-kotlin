package com.paligot.jsonforms.kotlin.models.uischema

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A layout which orders its children horizontally (i.e. from left to right).
 */
@Serializable
@SerialName("HorizontalLayout")
class HorizontalLayout(
    override val elements: ImmutableList<UiSchema> = persistentListOf(),
    override val rule: Rule? = null,
    override val options: LayoutOptions? = null
) : UiSchema()
