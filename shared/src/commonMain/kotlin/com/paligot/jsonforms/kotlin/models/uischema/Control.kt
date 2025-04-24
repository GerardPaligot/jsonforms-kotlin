package com.paligot.jsonforms.kotlin.models.uischema

import com.paligot.jsonforms.kotlin.models.serializers.ImmutableListSerializer
import kotlinx.collections.immutable.ImmutableList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A control element which represent a UI element in the form.
 */
@Serializable
@SerialName("Control")
data class Control(
    /**
     * The scope that determines to which part this element should be bound to.
     */
    val scope: String,
    /**
     * Label for UI schema element.
     */
    var label: String? = null,
    /**
     * Any additional options.
     */
    override val options: ControlOptions? = null,
    @Serializable(with = ImmutableListSerializer::class)
    override val elements: ImmutableList<UiSchema>? = null,
    override val rule: Rule? = null,
) : UiSchema()
