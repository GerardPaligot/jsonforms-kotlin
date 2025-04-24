package com.paligot.jsonforms.kotlin.models.uischema

import kotlinx.serialization.Serializable

/**
 * Options for a layout element.
 */
@Serializable
data class LayoutOptions(
    /**
     * The vertical spacing between elements.
     */
    val verticalSpacing: String? = null,
    /**
     * The horizontal spacing between elements.
     */
    val horizontalSpacing: String? = null,
    override val weight: Float? = null,
) : Options
