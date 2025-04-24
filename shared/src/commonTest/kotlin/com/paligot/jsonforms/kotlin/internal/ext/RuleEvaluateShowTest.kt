package com.paligot.jsonforms.kotlin.internal.ext

import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import com.paligot.jsonforms.kotlin.models.uischema.Condition
import com.paligot.jsonforms.kotlin.models.uischema.Effect
import com.paligot.jsonforms.kotlin.models.uischema.Rule
import kotlinx.serialization.json.JsonPrimitive
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RuleEvaluateShowTest {
    @Test
    fun `evaluateShow should return true when effect is Show and condition resolves to true`() {
        val rule = Rule(
            effect = Effect.Show,
            condition = Condition(
                scope = "#/properties/key",
                schema = StringProperty(const = JsonPrimitive("value"))
            )
        )
        val data = mapOf("key" to "value")

        val result = rule.evaluateShow(data)

        assertTrue(result)
    }

    @Test
    fun `evaluateShow should return false when effect is Show and condition resolves to false`() {
        val rule = Rule(
            effect = Effect.Show,
            condition = Condition(
                scope = "#/properties/key",
                schema = StringProperty(const = JsonPrimitive("value"))
            )
        )
        val data = mapOf("key" to "otherValue")

        val result = rule.evaluateShow(data)

        assertFalse(result)
    }

    @Test
    fun `evaluateShow should return false when effect is Hide and condition resolves to true`() {
        val rule = Rule(
            effect = Effect.Hide,
            condition = Condition(
                scope = "#/properties/key",
                schema = StringProperty(const = JsonPrimitive("value"))
            )
        )
        val data = mapOf("key" to "value")

        val result = rule.evaluateShow(data)

        assertFalse(result)
    }

    @Test
    fun `evaluateShow should return true when effect is Hide and condition resolves to false`() {
        val rule = Rule(
            effect = Effect.Hide,
            condition = Condition(
                scope = "#/properties/key",
                schema = StringProperty(const = JsonPrimitive("value"))
            )
        )
        val data = mapOf("key" to "otherValue")

        val result = rule.evaluateShow(data)

        assertTrue(result)
    }

    @Test
    fun `evaluateShow should return true when condition schema is empty`() {
        val rule = Rule(
            effect = Effect.Show,
            condition = Condition(
                scope = "#/properties/key",
                schema = StringProperty()
            )
        )
        val data = mapOf("key" to "value")

        val result = rule.evaluateShow(data)

        assertTrue(result)
    }

    @Test
    fun `evaluateShow should return true when effect is Show and value matches the pattern`() {
        val rule = Rule(
            effect = Effect.Show,
            condition = Condition(
                scope = "#/properties/key",
                schema = StringProperty(pattern = "^[a-zA-Z]+$")
            )
        )
        val data = mapOf("key" to "validValue")

        val result = rule.evaluateShow(data)

        assertTrue(result)
    }

    @Test
    fun `evaluateShow should return false when effect is Show and value does not match the pattern`() {
        val rule = Rule(
            effect = Effect.Show,
            condition = Condition(
                scope = "#/properties/key",
                schema = StringProperty(pattern = "^[a-zA-Z]+$")
            )
        )
        val data = mapOf("key" to "12345")

        val result = rule.evaluateShow(data)

        assertFalse(result)
    }

    @Test
    fun `evaluateShow should return false when effect is Hide and value matches the pattern`() {
        val rule = Rule(
            effect = Effect.Hide,
            condition = Condition(
                scope = "#/properties/key",
                schema = StringProperty(pattern = "^[a-zA-Z]+$")
            )
        )
        val data = mapOf("key" to "validValue")

        val result = rule.evaluateShow(data)

        assertFalse(result)
    }

    @Test
    fun `evaluateShow should return true when effect is Hide and value does not match the pattern`() {
        val rule = Rule(
            effect = Effect.Hide,
            condition = Condition(
                scope = "#/properties/key",
                schema = StringProperty(pattern = "^[a-zA-Z]+$")
            )
        )
        val data = mapOf("key" to "12345")

        val result = rule.evaluateShow(data)

        assertTrue(result)
    }
}