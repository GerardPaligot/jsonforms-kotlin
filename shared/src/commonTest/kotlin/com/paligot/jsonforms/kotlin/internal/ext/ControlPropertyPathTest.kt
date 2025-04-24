package com.paligot.jsonforms.kotlin.internal.ext

import com.paligot.jsonforms.kotlin.models.uischema.Control
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertFailsWith

class ControlPropertyPathTest {
    @Test
    fun `propertyPath should return single key for simple scope`() {
        val control = Control(scope = "#/properties/key")
        val result = control.propertyPath()
        assertContentEquals(arrayOf("key"), result)
    }

    @Test
    fun `propertyPath should return multiple keys for nested scope`() {
        val control = Control(scope = "#/properties/key1/properties/key2")
        val result = control.propertyPath()
        assertContentEquals(arrayOf("key1", "key2"), result)
    }

    @Test
    fun `propertyPath should throw an error for malformed scope`() {
        val control = Control(scope = "invalid_scope")
        assertFailsWith<IllegalStateException> { control.propertyPath() }
    }

    @Test
    fun `propertyPath should handle scope with multiple nested properties`() {
        val control = Control(scope = "#/properties/key1/properties/key2/properties/key3")
        val result = control.propertyPath()
        assertContentEquals(arrayOf("key1", "key2", "key3"), result)
    }

    @Test
    fun `propertyPath should throw an error for empty scope`() {
        val control = Control(scope = "")
        assertFailsWith<IllegalStateException> { control.propertyPath() }
    }
}
