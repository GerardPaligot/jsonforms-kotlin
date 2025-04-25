package com.paligot.jsonforms.ui

import androidx.compose.ui.test.junit4.createComposeRule
import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import com.paligot.jsonforms.kotlin.models.uischema.Condition
import com.paligot.jsonforms.kotlin.models.uischema.Control
import com.paligot.jsonforms.kotlin.models.uischema.Effect
import com.paligot.jsonforms.kotlin.models.uischema.GroupLayout
import com.paligot.jsonforms.kotlin.models.uischema.HorizontalLayout
import com.paligot.jsonforms.kotlin.models.uischema.VerticalLayout
import io.mockk.every
import io.mockk.mockk
import kotlinx.serialization.json.JsonPrimitive
import org.junit.Rule
import org.junit.Test

class LayoutTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `renders VerticalLayout with children`() {
        val uiSchema = VerticalLayout()
        val jsonFormState = mockk<JsonFormState>()

        every { jsonFormState.getData() } returns mapOf()

        composeTestRule.setContent {
            Layout(
                uiSchema = uiSchema,
                jsonFormState = jsonFormState,
                layoutContent = { assert(this.isVerticalLayout()) },
                content = { error("Should not render Control") },
            )
        }
    }

    @Test
    fun `renders HorizontalLayout with children`() {
        val uiSchema = HorizontalLayout()
        val jsonFormState = mockk<JsonFormState>()

        every { jsonFormState.getData() } returns mapOf()

        composeTestRule.setContent {
            Layout(
                uiSchema = uiSchema,
                jsonFormState = jsonFormState,
                layoutContent = { assert(this.isHorizontalLayout()) },
                content = { error("Should not render Control") },
            )
        }
    }

    @Test
    fun `renders GroupLayout with children`() {
        val uiSchema = GroupLayout(label = "Group Label")
        val jsonFormState = mockk<JsonFormState>()

        every { jsonFormState.getData() } returns mapOf()

        composeTestRule.setContent {
            Layout(
                uiSchema = uiSchema,
                jsonFormState = jsonFormState,
                layoutContent = { assert(this.isGroupLayout()) },
                content = { error("Should not render Control") },
            )
        }
    }

    @Test
    fun `renders Control`() {
        val uiSchema = Control(scope = "#/properties/string")
        val jsonFormState = mockk<JsonFormState>()

        every { jsonFormState.getData() } returns mapOf()

        composeTestRule.setContent {
            Layout(
                uiSchema = uiSchema,
                jsonFormState = jsonFormState,
                layoutContent = { error("Should not render layout") },
                content = { control -> assert(control.scope == "#/properties/string") },
            )
        }
    }

    @Test
    fun `hides layout when rule evaluates to false`() {
        val uiSchema =
            GroupLayout(
                label = "Hidden Group",
                rule =
                    com.paligot.jsonforms.kotlin.models.uischema.Rule(
                        effect = Effect.Show,
                        condition =
                            Condition(
                                scope = "#/properties/not_exist",
                                schema = StringProperty(const = JsonPrimitive("not exist")),
                            ),
                    ),
            )
        val jsonFormState = mockk<JsonFormState>()

        every { jsonFormState.getData() } returns mapOf()

        composeTestRule.setContent {
            Layout(
                uiSchema = uiSchema,
                jsonFormState = jsonFormState,
                layoutContent = { error("Should not render layout") },
                content = { error("Should not render Control") },
            )
        }
    }
}
