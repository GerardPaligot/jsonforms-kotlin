package com.paligot.jsonforms.kotlin.models.uischema

import com.paligot.jsonforms.kotlin.models.serializers.ImmutableListSerializer
import kotlinx.collections.immutable.ImmutableList
import kotlinx.serialization.Serializable

/**
 * Common base sealed class for any UI schema element.
 */
@Serializable
sealed class UiSchema {
    /**
     * The child elements of this layout.
     */
    @Serializable(with = ImmutableListSerializer::class)
    abstract val elements: ImmutableList<UiSchema>?

    /**
     * The optional options applied of the element.
     */
    abstract val options: Options?

    /**
     * An optional rule.
     */
    abstract val rule: Rule?
}

interface Options {
    val weight: Float?
}
