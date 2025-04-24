package com.paligot.jsonforms.kotlin.internal.ext

import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import com.paligot.jsonforms.kotlin.models.uischema.Control
import com.paligot.jsonforms.kotlin.models.uischema.ControlOptions
import com.paligot.jsonforms.kotlin.models.uischema.Format
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.json.JsonPrimitive
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class StringPropertyIsRadioTest {
    @Test
    fun `isRadio should return true when format is Radio and enum is not null or empty`() {
        val property = StringProperty(enum = persistentListOf("option1", "option2"))
        val control =
            Control(
                scope = "#/properties/key",
                options = ControlOptions(format = Format.Radio),
            )

        val result = property.isRadio(control)

        assertTrue(result)
    }

    @Test
    fun `isRadio should return true when format is Radio and oneOf is not null or empty`() {
        val property =
            StringProperty(
                oneOf =
                    persistentListOf(
                        StringProperty(const = JsonPrimitive("value1"), title = "Title1"),
                    ),
            )
        val control =
            Control(
                scope = "#/properties/key",
                options = ControlOptions(format = Format.Radio),
            )

        val result = property.isRadio(control)

        assertTrue(result)
    }

    @Test
    fun `isRadio should return false when format is not Radio`() {
        val property = StringProperty(enum = persistentListOf("option1", "option2"))
        val control = Control(scope = "#/properties/key")

        val result = property.isRadio(control)

        assertFalse(result)
    }

    @Test
    fun `isRadio should return false when enum and oneOf are both null`() {
        val property = StringProperty()
        val control =
            Control(
                scope = "#/properties/key",
                options = ControlOptions(format = Format.Radio),
            )

        val result = property.isRadio(control)

        assertFalse(result)
    }

    @Test
    fun `isRadio should return false when enum and oneOf are empty`() {
        val property = StringProperty(enum = persistentListOf(), oneOf = persistentListOf())
        val control =
            Control(
                scope = "#/properties/key",
                options = ControlOptions(format = Format.Radio),
            )

        val result = property.isRadio(control)

        assertFalse(result)
    }

    @Test
    fun `isRadio should return false when control options are null`() {
        val property = StringProperty(enum = persistentListOf("option1"))
        val control = Control(scope = "#/properties/key", options = null)

        val result = property.isRadio(control)

        assertFalse(result)
    }
}
