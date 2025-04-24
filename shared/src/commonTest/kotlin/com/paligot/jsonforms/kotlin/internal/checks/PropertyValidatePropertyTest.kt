package com.paligot.jsonforms.kotlin.internal.checks

import com.paligot.jsonforms.kotlin.internal.FieldError
import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import kotlinx.serialization.json.JsonPrimitive
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PropertyValidatePropertyTest {

    @Test
    fun `validateProperty should return no errors when value matches const`() {
        val property = StringProperty(const = JsonPrimitive("expectedValue"))
        val result = property.validateProperty("fieldId", "expectedValue")
        assertTrue(result.isEmpty(), "Expected no errors when value matches const")
    }

    @Test
    fun `validateProperty should return error when value does not match const`() {
        val property = StringProperty(const = JsonPrimitive("expectedValue"))
        val result = property.validateProperty("fieldId", "wrongValue")
        assertEquals(1, result.size)
        assertTrue(result.first() is FieldError.InvalidValueFieldError)
    }

    @Test
    fun `validateProperty should return no errors when value matches pattern`() {
        val property = StringProperty(pattern = "\\d{3}-\\d{2}-\\d{4}")
        val result = property.validateProperty("fieldId", "123-45-6789")
        assertTrue(result.isEmpty(), "Expected no errors when value matches pattern")
    }

    @Test
    fun `validateProperty should return error when value does not match pattern`() {
        val property = StringProperty(pattern = "\\d{3}-\\d{2}-\\d{4}")
        val result = property.validateProperty("fieldId", "invalid-pattern")
        assertEquals(1, result.size)
        assertTrue(result.first() is FieldError.PatternFieldError)
    }

    @Test
    fun `validateProperty should return no errors when value does not match not constraint`() {
        val notProperty = StringProperty(const = JsonPrimitive("forbiddenValue"))
        val property = StringProperty(not = notProperty)
        val result = property.validateProperty("fieldId", "allowedValue")
        assertTrue(result.isEmpty(), "Expected no errors when value does not match not constraint")
    }

    @Test
    fun `validateProperty should return error when value matches not constraint`() {
        val notProperty = StringProperty(const = JsonPrimitive("forbiddenValue"))
        val property = StringProperty(not = notProperty)
        val result = property.validateProperty("fieldId", "forbiddenValue")
        assertEquals(1, result.size)
        assertTrue(result.first() is FieldError.InvalidNotPropertyError)
    }
}
