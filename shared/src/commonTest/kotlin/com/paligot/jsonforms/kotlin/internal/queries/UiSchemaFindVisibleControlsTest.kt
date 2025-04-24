package com.paligot.jsonforms.kotlin.internal.queries

import com.paligot.jsonforms.kotlin.internal.ext.propertyKey
import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import com.paligot.jsonforms.kotlin.models.uischema.*
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.json.JsonPrimitive
import kotlin.test.Test
import kotlin.test.assertEquals

class UiSchemaFindVisibleControlsTest {

    @Test
    fun `findVisibleKeys should return keys for visible controls`() {
        val control1 = Control(scope = "#/properties/key1", rule = null)
        val control2 = Control(scope = "#/properties/key2", rule = null)
        val layout = VerticalLayout(elements = persistentListOf(control1, control2))
        val data = mapOf<String, Any?>()

        val result = layout.findVisibleControls(data)

        assertEquals(listOf("key1", "key2"), result.map { it.propertyKey() })
    }

    @Test
    fun `findVisibleKeys should return empty list when all controls are hidden`() {
        val control1 = Control(scope = "#/properties/key1", rule = Rule(
            effect = Effect.Show,
            condition = Condition(
                scope = "#/properties/key1",
                schema = StringProperty(const = JsonPrimitive("invalid_value")))
            )
        )
        val control2 = Control(scope = "#/properties/key2", rule = Rule(
            effect = Effect.Show,
            condition = Condition(
                scope = "#/properties/key2",
                schema = StringProperty(const = JsonPrimitive("invalid_value")))
            )
        )
        val layout = VerticalLayout(elements = persistentListOf(control1, control2))
        val data = mapOf<String, Any?>()

        val result = layout.findVisibleControls(data)

        assertEquals(emptyList(), result)
    }

    @Test
    fun `findVisibleKeys should return keys only for visible controls`() {
        val control1 = Control(scope = "#/properties/key1", rule = Rule(
            effect = Effect.Show,
            condition = Condition(
                scope = "#/properties/key1",
                schema = StringProperty(const = JsonPrimitive("valid")))
            )
        )
        val control2 = Control(scope = "#/properties/key2", rule = Rule(
            effect = Effect.Show,
            condition = Condition(
                scope = "#/properties/key2",
                schema = StringProperty(const = JsonPrimitive("invalid")))
            )
        )
        val layout = VerticalLayout(elements = persistentListOf(control1, control2))
        val data = mapOf<String, Any?>("key1" to "valid")

        val result = layout.findVisibleControls(data)

        assertEquals(listOf("key1"), result.map { it.propertyKey() })
    }

    @Test
    fun `findVisibleKeys should handle nested layouts`() {
        val control1 = Control(scope = "#/properties/key1", rule = null)
        val control2 = Control(scope = "#/properties/key2", rule = null)
        val nestedLayout = HorizontalLayout(elements = persistentListOf(control2))
        val layout = VerticalLayout(elements = persistentListOf(control1, nestedLayout))
        val data = mapOf<String, Any?>()

        val result = layout.findVisibleControls(data)

        assertEquals(listOf("key1", "key2"), result.map { it.propertyKey() })
    }

    @Test
    fun `findVisibleKeys should return empty list for empty layout`() {
        val layout = VerticalLayout(elements = persistentListOf())
        val data = mapOf<String, Any?>()

        val result = layout.findVisibleControls(data)

        assertEquals(emptyList(), result)
    }
}
