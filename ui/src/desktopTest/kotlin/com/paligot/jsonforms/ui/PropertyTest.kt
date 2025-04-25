package com.paligot.jsonforms.ui

import androidx.compose.ui.test.junit4.createComposeRule
import com.paligot.jsonforms.kotlin.SchemaProvider
import com.paligot.jsonforms.kotlin.models.schema.BooleanProperty
import com.paligot.jsonforms.kotlin.models.schema.NumberProperty
import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import com.paligot.jsonforms.kotlin.models.uischema.Control
import io.mockk.every
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class PropertyTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `renders StringProperty with correct scope`() {
        val control = Control(scope = "#/properties/string")
        val schemaProvider = mockk<SchemaProvider>()
        val jsonFormState = mockk<JsonFormState>()
        val property = StringProperty()

        every { schemaProvider.getPropertyByControl<StringProperty>(control) } returns property

        composeTestRule.setContent {
            Property(
                control = control,
                schemaProvider = schemaProvider,
                jsonFormState = jsonFormState,
                stringContent = { id -> assert(id == "string") },
                numberContent = { error("Should not render NumberProperty") },
                booleanContent = { error("Should not render BooleanProperty") },
            )
        }
    }

    @Test
    fun `renders BooleanProperty with correct scope`() {
        val control = Control(scope = "#/properties/boolean")
        val schemaProvider = mockk<SchemaProvider>()
        val jsonFormState = mockk<JsonFormState>()
        val property = BooleanProperty()

        every { schemaProvider.getPropertyByControl<BooleanProperty>(control) } returns property

        composeTestRule.setContent {
            Property(
                control = control,
                schemaProvider = schemaProvider,
                jsonFormState = jsonFormState,
                stringContent = { error("Should not render StringProperty") },
                numberContent = { error("Should not render NumberProperty") },
                booleanContent = { id -> assert(id == "boolean") },
            )
        }
    }

    @Test
    fun `renders NumberProperty with correct scope`() {
        val control = Control(scope = "#/properties/number")
        val schemaProvider = mockk<SchemaProvider>()
        val jsonFormState = mockk<JsonFormState>()
        val property = NumberProperty()

        every { schemaProvider.getPropertyByControl<NumberProperty>(control) } returns property

        composeTestRule.setContent {
            Property(
                control = control,
                schemaProvider = schemaProvider,
                jsonFormState = jsonFormState,
                stringContent = { error("Should not render StringProperty") },
                numberContent = { id -> assert(id == "number") },
                booleanContent = { error("Should not render BooleanProperty") },
            )
        }
    }
}
