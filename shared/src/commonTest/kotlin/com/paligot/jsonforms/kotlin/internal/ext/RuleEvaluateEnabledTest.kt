package com.paligot.jsonforms.kotlin.internal.ext

import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import com.paligot.jsonforms.kotlin.models.uischema.Condition
import com.paligot.jsonforms.kotlin.models.uischema.Effect
import com.paligot.jsonforms.kotlin.models.uischema.Rule
import kotlinx.serialization.json.JsonPrimitive
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RuleEvaluateEnabledTest {
    @Test
    fun `evaluateEnabled should return true when effect is Enable and condition resolves to true`() {
        val rule =
            Rule(
                effect = Effect.Enable,
                condition =
                    Condition(
                        scope = "#/properties/key",
                        schema = StringProperty(const = JsonPrimitive("value")),
                    ),
            )
        val data = mapOf("key" to "value")

        val result = rule.evaluateEnabled(data)

        assertTrue(result)
    }

    @Test
    fun `evaluateEnabled should return false when effect is Enable and condition resolves to false`() {
        val rule =
            Rule(
                effect = Effect.Enable,
                condition =
                    Condition(
                        scope = "#/properties/key",
                        schema = StringProperty(const = JsonPrimitive("value")),
                    ),
            )
        val data = mapOf("key" to "otherValue")

        val result = rule.evaluateEnabled(data)

        assertFalse(result)
    }

    @Test
    fun `evaluateEnabled should return false when effect is Disable and condition resolves to true`() {
        val rule =
            Rule(
                effect = Effect.Disable,
                condition =
                    Condition(
                        scope = "#/properties/key",
                        schema = StringProperty(const = JsonPrimitive("value")),
                    ),
            )
        val data = mapOf("key" to "value")

        val result = rule.evaluateEnabled(data)

        assertFalse(result)
    }

    @Test
    fun `evaluateEnabled should return true when effect is Disable and condition resolves to false`() {
        val rule =
            Rule(
                effect = Effect.Disable,
                condition =
                    Condition(
                        scope = "#/properties/key",
                        schema = StringProperty(const = JsonPrimitive("value")),
                    ),
            )
        val data = mapOf("key" to "otherValue")

        val result = rule.evaluateEnabled(data)

        assertTrue(result)
    }

    @Test
    fun `evaluateEnabled should return true when condition schema is empty`() {
        val rule =
            Rule(
                effect = Effect.Enable,
                condition =
                    Condition(
                        scope = "#/properties/key",
                        schema = StringProperty(),
                    ),
            )
        val data = mapOf("key" to "value")

        val result = rule.evaluateEnabled(data)

        assertTrue(result)
    }
}
