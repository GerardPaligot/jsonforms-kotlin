package com.paligot.jsonforms.kotlin.internal.ext

import com.paligot.jsonforms.kotlin.models.schema.NumberProperty
import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import com.paligot.jsonforms.kotlin.models.uischema.Control
import com.paligot.jsonforms.kotlin.models.uischema.ControlOptions
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PropertyMaxCounterTest {
    @Test
    fun `getMaxCounter should return null when showMaxCounter is false`() {
        val property = StringProperty(maxLength = 10)
        val control = Control(scope = "#/properties/string", options = ControlOptions(showMaxCounter = false))

        val result = property.getMaxCounter("value", control)

        assertNull(result)
    }

    @Test
    fun `getMaxCounter should return null when maxLength is null for StringProperty`() {
        val property = StringProperty(maxLength = null)
        val control = Control(scope = "#/properties/string", options = ControlOptions(showMaxCounter = true))

        val result = property.getMaxCounter("value", control)

        assertNull(result)
    }

    @Test
    fun `getMaxCounter should return correct Pair for StringProperty`() {
        val property = StringProperty(maxLength = 10)
        val control = Control(scope = "#/properties/string", options = ControlOptions(showMaxCounter = true))

        val result = property.getMaxCounter("value", control)

        assertEquals(Pair(5, 10), result)
    }

    @Test
    fun `getMaxCounter should return null when maximum is null for NumberProperty`() {
        val property = NumberProperty(maximum = null)
        val control = Control(scope = "#/properties/number", options = ControlOptions(showMaxCounter = true))

        val result = property.getMaxCounter("123", control)

        assertNull(result)
    }

    @Test
    fun `getMaxCounter should return correct Pair for NumberProperty`() {
        val property = NumberProperty(maximum = 100)
        val control = Control(scope = "#/properties/number", options = ControlOptions(showMaxCounter = true))

        val result = property.getMaxCounter("47", control)

        assertEquals(Pair(47, 100), result)
    }

    @Test
    fun `getMaxCounter should handle empty string for NumberProperty`() {
        val property = NumberProperty(maximum = 100)
        val control = Control(scope = "#/properties/number", options = ControlOptions(showMaxCounter = true))

        val result = property.getMaxCounter("", control)

        assertEquals(Pair(0, 100), result)
    }
}
