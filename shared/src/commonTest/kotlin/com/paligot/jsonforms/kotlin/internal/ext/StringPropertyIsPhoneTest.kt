package com.paligot.jsonforms.kotlin.internal.ext

import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import com.paligot.jsonforms.kotlin.models.uischema.Control
import com.paligot.jsonforms.kotlin.models.uischema.ControlOptions
import com.paligot.jsonforms.kotlin.models.uischema.Format
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class StringPropertyIsPhoneTest {

    @Test
    fun `isPhone should return true when format is Phone`() {
        val property = StringProperty()
        val control = Control(
            scope = "#/properties/key",
            options = ControlOptions(format = Format.Phone)
        )

        val result = property.isPhone(control)

        assertTrue(result)
    }

    @Test
    fun `isPhone should return false when format is not Phone`() {
        val property = StringProperty()
        val control = Control(
            scope = "#/properties/key",
            options = ControlOptions(format = Format.Email)
        )

        val result = property.isPhone(control)

        assertFalse(result)
    }

    @Test
    fun `isPhone should return false when control options are null`() {
        val property = StringProperty()
        val control = Control(scope = "#/properties/key", options = null)

        val result = property.isPhone(control)

        assertFalse(result)
    }
}
