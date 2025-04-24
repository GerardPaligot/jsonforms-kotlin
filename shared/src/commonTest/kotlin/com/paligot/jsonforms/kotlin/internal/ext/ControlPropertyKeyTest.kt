package com.paligot.jsonforms.kotlin.internal.ext

import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import com.paligot.jsonforms.kotlin.models.uischema.Condition
import com.paligot.jsonforms.kotlin.models.uischema.Control
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ControlPropertyKeyTest {
    @Test
    fun `propertyKey should return the last key in the scope for Control`() {
        val control = Control(scope = "#/properties/key")
        val result = control.propertyKey()
        assertEquals("key", result)
    }

    @Test
    fun `propertyKey should return the last key in the scope for Condition`() {
        val condition = Condition(scope = "#/properties/key", schema = StringProperty())
        val result = condition.propertyKey()
        assertEquals("key", result)
    }

    @Test
    fun `propertyKey should handle nested properties in the scope for Control`() {
        val control = Control(scope = "#/properties/key1/properties/key2")
        val result = control.propertyKey()
        assertEquals("key2", result)
    }

    @Test
    fun `propertyKey should handle nested properties in the scope for Condition`() {
        val condition =
            Condition(scope = "#/properties/key1/properties/key2", schema = StringProperty())
        val result = condition.propertyKey()
        assertEquals("key2", result)
    }

    @Test
    fun `propertyKey should throw an error for malformed scope in Control`() {
        val control = Control(scope = "invalid_scope")
        assertFailsWith<IllegalStateException> { control.propertyKey() }
    }

    @Test
    fun `propertyKey should throw an error for malformed scope in Condition`() {
        val condition = Condition(scope = "invalid_scope", schema = StringProperty())
        assertFailsWith<IllegalStateException> { condition.propertyKey() }
    }
}
