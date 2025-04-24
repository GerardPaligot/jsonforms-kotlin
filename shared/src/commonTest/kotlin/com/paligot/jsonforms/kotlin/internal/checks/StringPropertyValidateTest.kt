package com.paligot.jsonforms.kotlin.internal.checks

import com.paligot.jsonforms.kotlin.internal.FieldError
import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import kotlinx.collections.immutable.persistentListOf
import kotlin.test.Test
import kotlin.test.assertEquals

class StringPropertyValidateTest {

    @Test
    fun `validate should return no errors for a valid string`() {
        val property = StringProperty(minLength = 3, maxLength = 10)
        val scopeKey = "key"
        val value = "valid"

        val result = property.validate(scopeKey, value)

        assertEquals(emptyList(), result)
    }

    @Test
    fun `validate should return an error for a string shorter than minLength`() {
        val property = StringProperty(minLength = 5)
        val scopeKey = "key"
        val value = "abc"

        val result = property.validate(scopeKey, value).first()

        assertEquals(
            FieldError.MinLengthFieldError(5, scopeKey).message,
            result.message
        )
    }

    @Test
    fun `validate should return an error for a string longer than maxLength`() {
        val property = StringProperty(maxLength = 5)
        val scopeKey = "key"
        val value = "exceeds"

        val result = property.validate(scopeKey, value).first()

        assertEquals(
            FieldError.MaxLengthFieldError(5, scopeKey).message,
            result.message
        )
    }

    @Test
    fun `validate should return an error for a string not in enum`() {
        val property = StringProperty(enum = persistentListOf("allowed1", "allowed2"))
        val scopeKey = "key"
        val value = "notAllowed"

        val result = property.validate(scopeKey, value).first()

        assertEquals(
            FieldError.InvalidEnumFieldError(listOf("allowed1", "allowed2"), scopeKey).message,
            result.message
        )
    }
}
