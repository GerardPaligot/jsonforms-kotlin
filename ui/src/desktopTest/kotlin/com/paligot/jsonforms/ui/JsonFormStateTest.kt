package com.paligot.jsonforms.ui

import androidx.compose.runtime.State
import androidx.compose.ui.test.junit4.createComposeRule
import com.paligot.jsonforms.kotlin.internal.FieldError
import com.paligot.jsonforms.kotlin.models.schema.ObjectProperty
import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import com.paligot.jsonforms.kotlin.models.uischema.Control
import com.paligot.jsonforms.kotlin.models.uischema.VerticalLayout
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.JsonPrimitive
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class JsonFormStateTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `set() updates the value of a field`() {
        val jsonFormState = JsonFormStateImpl(mapOf("field1" to "value1"))
        jsonFormState["field1"] = "newValue"

        assertEquals("newValue", jsonFormState.getValue("field1"))
    }

    @Test
    fun `getData() returns the current form data`() {
        val initialData = mapOf("field1" to "value1", "field2" to 42)
        val jsonFormState = JsonFormStateImpl(initialData)

        assertEquals(initialData, jsonFormState.getData())
    }

    @Test
    fun `validate() returns true when there are no errors`() =
        runBlocking {
            val jsonFormState = JsonFormStateImpl(mapOf("field1" to "value1"))
            val schema =
                ObjectProperty(
                    properties = persistentMapOf("field1" to StringProperty()),
                )
            val uiSchema =
                VerticalLayout(
                    elements = persistentListOf(Control(scope = "#/properties/field1")),
                )

            val result = jsonFormState.validate(schema, uiSchema)

            assertTrue(result)
        }

    @Test
    fun `validate() returns false when there are errors`() =
        runBlocking {
            val jsonFormState = JsonFormStateImpl(mapOf("field1" to "invalid"))
            val schema =
                ObjectProperty(
                    properties = persistentMapOf("field1" to StringProperty(pattern = "abc")),
                )
            val uiSchema =
                VerticalLayout(
                    elements = persistentListOf(Control(scope = "#/properties/field1")),
                )

            val result = jsonFormState.validate(schema, uiSchema)

            assertFalse(result)
        }

    @Test
    fun `markAsErrors() adds custom errors`() {
        val jsonFormState = JsonFormStateImpl(mapOf("field1" to "value1"))
        val errors =
            listOf(
                FieldError.InvalidValueFieldError(value = JsonPrimitive(""), scope = "field1"),
            )

        jsonFormState.markAsErrors(errors)

        composeTestRule.setContent {
            val errorState: State<FieldError?> = jsonFormState.error("field1")
            assertEquals("field1", errorState.value?.scope)
        }
    }

    @Test
    fun `get() observes changes to a field`() {
        val jsonFormState = JsonFormStateImpl(mapOf("field1" to "value1"))
        jsonFormState["field1"] = "newValue"
        composeTestRule.setContent {
            val state: State<Any?> = jsonFormState["field1"]
            assertEquals("newValue", state.value)
        }
    }

    @Test
    fun `error() observes changes to error state`() {
        val jsonFormState = JsonFormStateImpl(mapOf("field1" to "value1"))
        val errors =
            listOf(
                FieldError.InvalidValueFieldError(value = JsonPrimitive(""), scope = "field1"),
            )

        jsonFormState.markAsErrors(errors)
        composeTestRule.setContent {
            val errorState: State<FieldError?> = jsonFormState.error("field1")
            assertEquals("Field field1 has invalid value \"\"", errorState.value?.message)
        }
    }
}
