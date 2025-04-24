package com.paligot.jsonforms.kotlin.internal.ext

import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import com.paligot.jsonforms.kotlin.models.uischema.Control
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PropertyLabelTest {
    @Test
    fun `label should return property title with asterisk when required`() {
        val property = StringProperty(title = "My Label")
        val control = Control(scope = "#/properties/label")

        val result = property.label(required = true, control = control)

        assertEquals("My Label*", result)
    }

    @Test
    fun `label should return control label with asterisk when required and property title is null`() {
        val property = StringProperty(title = null)
        val control = Control(scope = "#/properties/label", label = "Control Label")

        val result = property.label(required = true, control = control)

        assertEquals("Control Label*", result)
    }

    @Test
    fun `label should return null when both property title and control label are null`() {
        val property = StringProperty(title = null)
        val control = Control(scope = "#/properties/label", label = null)

        val result = property.label(required = true, control = control)

        assertNull(result)
    }

    @Test
    fun `label should return property title without asterisk when not required`() {
        val property = StringProperty(title = "My Label")
        val control = Control(scope = "#/properties/label")

        val result = property.label(required = false, control = control)

        assertEquals("My Label", result)
    }

    @Test
    fun `label should return control label without asterisk when not required and property title is null`() {
        val property = StringProperty(title = null)
        val control = Control(scope = "#/properties/label", label = "Control Label")

        val result = property.label(required = false, control = control)

        assertEquals("Control Label", result)
    }
}
