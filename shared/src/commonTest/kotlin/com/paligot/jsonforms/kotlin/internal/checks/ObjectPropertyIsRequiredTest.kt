package com.paligot.jsonforms.kotlin.internal.checks

import com.paligot.jsonforms.kotlin.models.schema.ObjectProperty
import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import com.paligot.jsonforms.kotlin.models.uischema.Control
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ObjectPropertyIsRequiredTest {

    @Test
    fun `propertyIsRequired should return true when the property is in the required list`() {
        val schema = ObjectProperty(
            properties = persistentMapOf("key" to StringProperty()),
            required = persistentListOf("key")
        )
        val control = Control(scope = "#/properties/key")
        val data = mapOf<String, Any?>()

        val result = schema.propertyIsRequired(control, data)

        assertTrue(result)
    }

    @Test
    fun `propertyIsRequired should return true when the property is required by anyOf`() {
        val schema = ObjectProperty(
            properties = persistentMapOf("key" to StringProperty()),
            anyOf = persistentListOf(
                ObjectProperty(
                    properties = persistentMapOf(),
                    required = persistentListOf("key")
                )
            )
        )
        val control = Control(scope = "#/properties/key")
        val data = mapOf<String, Any?>("key" to "value")

        val result = schema.propertyIsRequired(control, data)

        assertTrue(result)
    }

    @Test
    fun `propertyIsRequired should return false when the property is not required`() {
        val schema = ObjectProperty(
            properties = persistentMapOf("key" to StringProperty())
        )
        val control = Control(scope = "#/properties/key")
        val data = mapOf<String, Any?>()

        val result = schema.propertyIsRequired(control, data)

        assertFalse(result)
    }

    @Test
    fun `propertyIsRequired should return false when the property does not exist`() {
        val schema = ObjectProperty(
            properties = persistentMapOf("key" to StringProperty())
        )
        val control = Control(scope = "#/properties/nonexistent")
        val data = mapOf<String, Any?>()

        assertFails { schema.propertyIsRequired(control, data) }
    }
}
