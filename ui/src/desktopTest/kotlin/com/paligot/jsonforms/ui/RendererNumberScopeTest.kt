package com.paligot.jsonforms.ui

import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import com.paligot.jsonforms.kotlin.SchemaProvider
import com.paligot.jsonforms.kotlin.models.schema.NumberProperty
import com.paligot.jsonforms.kotlin.models.uischema.Control
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RendererNumberScopeTest {
    @Test
    fun `label() returns the correct label`() {
        val schemaProvider = mockk<SchemaProvider>()
        val jsonFormState = mockk<JsonFormState>()
        val control =
            Control(
                scope = "#/properties/number",
                label = "My label",
            )
        val property = NumberProperty()

        every { schemaProvider.propertyIsRequired(control, any()) } returns true
        every { jsonFormState.getData() } returns mapOf()

        val scope = RendererNumberScopeInstance(control, schemaProvider, jsonFormState, property)

        assertEquals("My label*", scope.label())
    }

    @Test
    fun `description() returns the description`() {
        val control = mockk<Control>()
        val schemaProvider = mockk<SchemaProvider>()
        val jsonFormState = mockk<JsonFormState>()
        val property = NumberProperty(description = "Test description")

        val scope = RendererNumberScopeInstance(control, schemaProvider, jsonFormState, property)

        assertEquals("Test description", scope.description())
    }

    @Test
    fun `enabled() returns true if the field is enabled`() {
        val schemaProvider = mockk<SchemaProvider>()
        val jsonFormState = mockk<JsonFormState>()
        val control = Control(scope = "#/properties/number")
        val property = NumberProperty()

        every { jsonFormState.getData() } returns mapOf()

        val scope = RendererNumberScopeInstance(control, schemaProvider, jsonFormState, property)

        assertTrue(scope.enabled())
    }

    @Test
    fun `keyboardOptions() returns the correct options`() {
        val schemaProvider = mockk<SchemaProvider>()
        val jsonFormState = mockk<JsonFormState>()
        val control = Control(scope = "#/properties/number")
        val property = NumberProperty()

        val options =
            RendererNumberScopeInstance(control, schemaProvider, jsonFormState, property)
                .keyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    imeAction = ImeAction.Done,
                )

        assertEquals(KeyboardCapitalization.None, options.capitalization)
        assertEquals(KeyboardType.Number, options.keyboardType)
        assertEquals(ImeAction.Done, options.imeAction)
    }
}
