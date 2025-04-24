package com.paligot.jsonforms.kotlin.models.uischema

import kotlinx.serialization.Serializable

/**
 * An option for a control element.
 */
@Serializable
data class ControlOptions(
    /**
     * An optional format.
     */
    val format: Format? = null,
    /**
     * An optional orientation.
     */
    val orientation: Orientation? = null,
    /**
     * The vertical spacing between elements.
     */
    val verticalSpacing: String? = null,
    /**
     * The horizontal spacing between elements.
     */
    val horizontalSpacing: String? = null,
    /**
     * The boolean to know if the control element will be interactive.
     */
    val readOnly: Boolean = false,
    /**
     * The boolean to know if the control element will have a max counter.
     */
    val showMaxCounter: Boolean = false,
    /**
     * The boolean to know if the control element will have the first letter capitalized.
     */
    val hasFirstLetterCapitalized: Boolean = false,
    override val weight: Float? = null,
) : Options
