package com.paligot.jsonforms.kotlin.internal.ext

import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.json.JsonPrimitive
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class StringPropertyIsDropdownTest {

    @Test
    fun `isDropdown should return true when oneOf is not null and not empty`() {
        val property = StringProperty(
            oneOf = persistentListOf(
                StringProperty(const = JsonPrimitive("value1"), title = "Title1")
            )
        )

        val result = property.isDropdown()

        assertTrue(result)
    }

    @Test
    fun `isDropdown should return false when oneOf is null`() {
        val property = StringProperty(oneOf = null)

        val result = property.isDropdown()

        assertFalse(result)
    }

    @Test
    fun `isDropdown should return false when oneOf is empty`() {
        val property = StringProperty(oneOf = persistentListOf())

        val result = property.isDropdown()

        assertFalse(result)
    }
}
