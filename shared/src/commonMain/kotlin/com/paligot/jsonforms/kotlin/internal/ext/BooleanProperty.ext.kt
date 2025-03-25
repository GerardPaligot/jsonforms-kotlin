package com.paligot.jsonforms.kotlin.internal.ext

import com.paligot.jsonforms.kotlin.models.schema.BooleanProperty
import com.paligot.jsonforms.kotlin.models.uischema.Control
import com.paligot.jsonforms.kotlin.models.uischema.Format

/**
 * Check if [BooleanProperty] is a toggle based on the format.
 *
 * ```kotlin
 * val booleanProperty = BooleanProperty()
 * val control = Control(
 *     scope = "#/properties/boolean",
 *     options = Options(format = Format.Toggle)
 * )
 * val isToggle = booleanProperty.isToggle(control)
 * ```
 *
 * @param control Field contained in the [com.decathlon.jsonforms.models.uischeme.UiSchema]
 * @return true if the [BooleanProperty] is a toggle
 */
fun BooleanProperty.isToggle(control: Control): Boolean =
    control.options?.format == Format.Toggle
