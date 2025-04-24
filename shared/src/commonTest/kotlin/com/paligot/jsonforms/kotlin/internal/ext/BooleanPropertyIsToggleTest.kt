package com.paligot.jsonforms.kotlin.internal.ext

import com.paligot.jsonforms.kotlin.models.schema.BooleanProperty
import com.paligot.jsonforms.kotlin.models.uischema.Control
import com.paligot.jsonforms.kotlin.models.uischema.ControlOptions
import com.paligot.jsonforms.kotlin.models.uischema.Format
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BooleanPropertyIsToggleTest {
    @Test
    fun `isToggle should return true when control format is Toggle`() {
        val property = BooleanProperty()
        val control =
            Control(
                scope = "#/properties/boolean",
                options = ControlOptions(format = Format.Toggle),
            )

        val result = property.isToggle(control)

        assertTrue(result)
    }

    @Test
    fun `isToggle should return false when control options are null`() {
        val property = BooleanProperty()
        val control = Control(scope = "#/properties/boolean", options = null)

        val result = property.isToggle(control)

        assertFalse(result)
    }
}
