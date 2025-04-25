package com.paligot.jsonforms.ui

import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.paligot.jsonforms.kotlin.SchemaProvider
import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import com.paligot.jsonforms.kotlin.models.uischema.Control
import com.paligot.jsonforms.kotlin.models.uischema.ControlOptions
import com.paligot.jsonforms.kotlin.models.uischema.Format
import com.paligot.jsonforms.kotlin.models.uischema.Orientation
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RendererStringScopeTest {
    @Test
    fun `label() returns the correct label`() {
        val schemaProvider = mockk<SchemaProvider>()
        val jsonFormState = mockk<JsonFormState>()
        val control = Control(scope = "#/properties/string", label = "My label")
        val property = StringProperty()

        every { schemaProvider.propertyIsRequired(control, any()) } returns true
        every { jsonFormState.getData() } returns mapOf()

        val scope = RendererStringScopeInstance(control, schemaProvider, jsonFormState, property)

        assertEquals("My label*", scope.label())
    }

    @Test
    fun `description() returns the description`() {
        val control = mockk<Control>()
        val schemaProvider = mockk<SchemaProvider>()
        val jsonFormState = mockk<JsonFormState>()
        val property = StringProperty(description = "Test description")

        val scope = RendererStringScopeInstance(control, schemaProvider, jsonFormState, property)

        assertEquals("Test description", scope.description())
    }

    @Test
    fun `enabled() returns true if the field is enabled`() {
        val schemaProvider = mockk<SchemaProvider>()
        val jsonFormState = mockk<JsonFormState>()
        val control = Control(scope = "#/properties/string")
        val property = StringProperty()

        every { jsonFormState.getData() } returns mapOf()

        val scope = RendererStringScopeInstance(control, schemaProvider, jsonFormState, property)

        assertTrue(scope.enabled())
    }

    @Test
    fun `visualTransformation() returns PasswordVisualTransformation for password fields`() {
        val schemaProvider = mockk<SchemaProvider>()
        val jsonFormState = mockk<JsonFormState>()
        val control =
            Control(
                scope = "#/properties/password",
                options = ControlOptions(format = Format.Password),
            )
        val property = StringProperty()

        val scope = RendererStringScopeInstance(control, schemaProvider, jsonFormState, property)

        assertEquals(PasswordVisualTransformation(), scope.visualTransformation())
    }

    @Test
    fun `keyboardOptions() returns the correct options`() {
        val schemaProvider = mockk<SchemaProvider>()
        val jsonFormState = mockk<JsonFormState>()
        val control = Control(scope = "#/properties/string")
        val property = StringProperty()

        val options =
            RendererStringScopeInstance(control, schemaProvider, jsonFormState, property)
                .keyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                )

        assertEquals(KeyboardCapitalization.Sentences, options.capitalization)
        assertEquals(KeyboardType.Text, options.keyboardType)
        assertEquals(ImeAction.Done, options.imeAction)
    }

    @Test
    fun `orientation() returns the correct orientation`() {
        val schemaProvider = mockk<SchemaProvider>()
        val jsonFormState = mockk<JsonFormState>()
        val control =
            Control(
                scope = "#/properties/string",
                options = ControlOptions(orientation = Orientation.HORIZONTALLY),
            )
        val property = StringProperty()

        val scope = RendererStringScopeInstance(control, schemaProvider, jsonFormState, property)

        assertEquals(Orientation.HORIZONTALLY, scope.orientation())
    }
}
