package com.paligot.jsonforms.kotlin.internal.ext

import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import com.paligot.jsonforms.kotlin.models.uischema.Control
import com.paligot.jsonforms.kotlin.models.uischema.ControlOptions
import com.paligot.jsonforms.kotlin.models.uischema.Format
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class StringPropertyIsEmailTest {
    @Test
    fun `isEmail should return true when format is Email`() {
        val property = StringProperty()
        val control =
            Control(
                scope = "#/properties/key",
                options = ControlOptions(format = Format.Email),
            )

        val result = property.isEmail(control)

        assertTrue(result)
    }

    @Test
    fun `isEmail should return false when format is not Email`() {
        val property = StringProperty()
        val control =
            Control(
                scope = "#/properties/key",
                options = ControlOptions(format = Format.Password),
            )

        val result = property.isEmail(control)

        assertFalse(result)
    }

    @Test
    fun `isEmail should return false when control options are null`() {
        val property = StringProperty()
        val control = Control(scope = "#/properties/key", options = null)

        val result = property.isEmail(control)

        assertFalse(result)
    }
}
