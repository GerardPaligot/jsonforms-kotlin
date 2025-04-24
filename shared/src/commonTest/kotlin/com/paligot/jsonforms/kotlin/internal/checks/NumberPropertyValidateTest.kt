package com.paligot.jsonforms.kotlin.internal.checks

import com.paligot.jsonforms.kotlin.internal.FieldError
import com.paligot.jsonforms.kotlin.models.schema.NumberProperty
import kotlin.test.Test
import kotlin.test.assertEquals

class NumberPropertyValidateTest {

    @Test
    fun `validate should return no errors for a valid number within range`() {
        val property = NumberProperty(minimum = 1, maximum = 10)
        val scopeKey = "key"
        val value = "5.0"

        val result = property.validate(scopeKey, value)

        assertEquals(emptyList(), result)
    }

    @Test
    fun `validate should return an error for a number below the minimum`() {
        val property = NumberProperty(minimum = 1)
        val scopeKey = "key"
        val value = "0.5"

        val result = property.validate(scopeKey, value).first()

        assertEquals(
            FieldError.MinValueFieldError(1, scopeKey).scope,
            result.scope
        )
        assertEquals(
            FieldError.MinValueFieldError(1, scopeKey).message,
            result.message
        )
    }

    @Test
    fun `validate should return an error for a number above the maximum`() {
        val property = NumberProperty(maximum = 10)
        val scopeKey = "key"
        val value = "15.0"

        val result = property.validate(scopeKey, value).first()

        assertEquals(
            FieldError.MaxValueFieldError(10, scopeKey).scope,
            result.scope
        )
        assertEquals(
            FieldError.MaxValueFieldError(10, scopeKey).message,
            result.message
        )
    }

    @Test
    fun `validate should return an error for a malformed number`() {
        val property = NumberProperty()
        val scopeKey = "key"
        val value = "invalid"

        val result = property.validate(scopeKey, value).first()

        assertEquals(
            FieldError.MalformedFieldError(scopeKey).scope,
            result.scope
        )
        assertEquals(
            FieldError.MalformedFieldError(scopeKey).message,
            result.message
        )
    }
}
