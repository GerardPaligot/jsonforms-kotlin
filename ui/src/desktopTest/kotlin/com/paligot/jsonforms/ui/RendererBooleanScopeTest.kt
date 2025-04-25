package com.paligot.jsonforms.ui

import com.paligot.jsonforms.kotlin.SchemaProvider
import com.paligot.jsonforms.kotlin.models.schema.BooleanProperty
import com.paligot.jsonforms.kotlin.models.uischema.Control
import com.paligot.jsonforms.kotlin.models.uischema.ControlOptions
import com.paligot.jsonforms.kotlin.models.uischema.Format
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RendererBooleanScopeTest {
    @Test
    fun `test isToggle returns true when control is toggle`() {
        val schemaProvider = mockk<SchemaProvider>()
        val jsonFormState = mockk<JsonFormState>()
        val control =
            Control(
                scope = "#/properties/check",
                options = ControlOptions(format = Format.Toggle),
            )
        val booleanProperty = BooleanProperty()

        every { schemaProvider.getPropertyByControl<BooleanProperty>(control) } returns booleanProperty

        val scope =
            RendererBooleanScopeInstance(control, schemaProvider, jsonFormState, booleanProperty)

        assertTrue(scope.isToggle())
    }

    @Test
    fun `test label returns correct label`() {
        val schemaProvider = mockk<SchemaProvider>()
        val jsonFormState = mockk<JsonFormState>()
        val control =
            Control(
                scope = "#/properties/check",
                label = "Test label",
                options = ControlOptions(format = Format.Toggle),
            )
        val booleanProperty = BooleanProperty()

        every { schemaProvider.getPropertyByControl<BooleanProperty>(control) } returns booleanProperty
        every { schemaProvider.propertyIsRequired(control, any()) } returns true
        every { jsonFormState.getData() } returns mapOf()

        val scope =
            RendererBooleanScopeInstance(control, schemaProvider, jsonFormState, booleanProperty)

        assertEquals("Test label*", scope.label())
    }

    @Test
    fun `test description returns correct description`() {
        val schemaProvider = mockk<SchemaProvider>()
        val jsonFormState = mockk<JsonFormState>()
        val control = mockk<Control>()
        val booleanProperty = BooleanProperty(description = "Test Description")

        every { schemaProvider.getPropertyByControl<BooleanProperty>(control) } returns booleanProperty

        val scope =
            RendererBooleanScopeInstance(control, schemaProvider, jsonFormState, booleanProperty)

        assertEquals("Test Description", scope.description())
    }

    @Test
    fun `test enabled returns true when property is enabled`() {
        val schemaProvider = mockk<SchemaProvider>()
        val jsonFormState = mockk<JsonFormState>()
        val control = Control(scope = "#/properties/check")
        val booleanProperty = BooleanProperty()

        every { schemaProvider.getPropertyByControl<BooleanProperty>(control) } returns booleanProperty
        every { jsonFormState.getData() } returns mapOf()

        val scope =
            RendererBooleanScopeInstance(control, schemaProvider, jsonFormState, booleanProperty)

        assertTrue(scope.enabled())
    }
}
