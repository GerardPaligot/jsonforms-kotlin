package com.paligot.jsonforms.kotlin.internal.checks

import com.paligot.jsonforms.kotlin.models.schema.BooleanProperty
import kotlin.test.Test
import kotlin.test.assertEquals

class BooleanPropertyValidateTest {
    @Test
    fun `validate should return no errors for a valid true boolean value`() {
        val property = BooleanProperty()
        val scopeKey = "key"
        val value = true

        val result = property.validate(scopeKey, value)

        assertEquals(emptyList(), result)
    }

    @Test
    fun `validate should return no errors for an valid false boolean value`() {
        val property = BooleanProperty()
        val scopeKey = "key"
        val value = false

        val result = property.validate(scopeKey, value)

        assertEquals(emptyList(), result)
    }
}
