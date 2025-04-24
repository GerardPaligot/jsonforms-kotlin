package com.paligot.jsonforms.kotlin.internal.ext

import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import com.paligot.jsonforms.kotlin.models.uischema.Control
import com.paligot.jsonforms.kotlin.models.uischema.ControlOptions
import com.paligot.jsonforms.kotlin.models.uischema.Format
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class StringPropertyIsPasswordTest {

    @Test
    fun `isPassword should return true when format is Password`() {
        val property = StringProperty()
        val control = Control(
            scope = "#/properties/key",
            options = ControlOptions(format = Format.Password)
        )

        val result = property.isPassword(control)

        assertTrue(result)
    }

    @Test
    fun `isPassword should return false when format is not Password`() {
        val property = StringProperty()
        val control = Control(
            scope = "#/properties/key",
            options = ControlOptions(format = Format.Email)
        )

        val result = property.isPassword(control)

        assertFalse(result)
    }

    @Test
    fun `isPassword should return false when control options are null`() {
        val property = StringProperty()
        val control = Control(scope = "#/properties/key", options = null)

        val result = property.isPassword(control)

        assertFalse(result)
    }
}
