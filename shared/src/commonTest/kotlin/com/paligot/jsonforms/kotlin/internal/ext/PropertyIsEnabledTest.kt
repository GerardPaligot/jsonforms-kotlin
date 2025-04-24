package com.paligot.jsonforms.kotlin.internal.ext

import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import com.paligot.jsonforms.kotlin.models.uischema.Condition
import com.paligot.jsonforms.kotlin.models.uischema.Control
import com.paligot.jsonforms.kotlin.models.uischema.ControlOptions
import com.paligot.jsonforms.kotlin.models.uischema.Effect
import com.paligot.jsonforms.kotlin.models.uischema.Rule
import kotlinx.serialization.json.JsonPrimitive
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PropertyIsEnabledTest {
    @Test
    fun `isEnabled should return true when control has no rule and property is not read-only`() {
        val property = StringProperty(readOnly = false)
        val control = Control(scope = "#/properties/string")
        val data = emptyMap<String, Any?>()

        val result = property.isEnabled(control, data)

        assertTrue(result)
    }

    @Test
    fun `isEnabled should return false when property is read-only`() {
        val property = StringProperty(readOnly = true)
        val control = Control(scope = "#/properties/string")
        val data = emptyMap<String, Any?>()

        val result = property.isEnabled(control, data)

        assertFalse(result)
    }

    @Test
    fun `isEnabled should return true when control options are not read-only`() {
        val property = StringProperty()
        val control = Control(scope = "#/properties/string", options = ControlOptions(readOnly = false))
        val data = emptyMap<String, Any?>()

        val result = property.isEnabled(control, data)

        assertTrue(result)
    }

    @Test
    fun `isEnabled should return false when control rule with Disable effect evaluates to true`() {
        val property = StringProperty()
        val control = Control(
            scope = "#/properties/string",
            rule = Rule(
                effect = Effect.Disable,
                condition = Condition(
                    scope = "#/properties/otherField",
                    schema = StringProperty(const = JsonPrimitive("value"))
                )
            )
        )
        val data = mapOf("otherField" to "value")

        val result = property.isEnabled(control, data)

        assertFalse(result)
    }

    @Test
    fun `isEnabled should return true when control rule with Enable effect evaluates to true`() {
        val property = StringProperty()
        val control = Control(
            scope = "#/properties/string",
            rule = Rule(
                effect = Effect.Enable,
                condition = Condition(
                    scope = "#/properties/otherField",
                    schema = StringProperty(const = JsonPrimitive("value"))
                )
            )
        )
        val data = mapOf("otherField" to "value")

        val result = property.isEnabled(control, data)

        assertTrue(result)
    }
}
