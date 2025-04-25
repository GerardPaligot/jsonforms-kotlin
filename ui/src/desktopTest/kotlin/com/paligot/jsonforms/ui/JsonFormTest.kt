package com.paligot.jsonforms.ui

import androidx.compose.ui.test.junit4.createComposeRule
import com.paligot.jsonforms.kotlin.models.schema.BooleanProperty
import com.paligot.jsonforms.kotlin.models.schema.NumberProperty
import com.paligot.jsonforms.kotlin.models.schema.Schema
import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import com.paligot.jsonforms.kotlin.models.uischema.Control
import com.paligot.jsonforms.kotlin.models.uischema.VerticalLayout
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import org.junit.Rule
import org.junit.Test

class JsonFormTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `renders string content`() {
        val schema =
            Schema(
                properties = persistentMapOf("stringField" to StringProperty()),
            )
        val uiSchema = Control(scope = "#/properties/stringField")
        composeTestRule.setContent {
            val state = rememberJsonFormState(initialValues = mutableMapOf())
            JsonForm(
                schema = schema,
                uiSchema = uiSchema,
                state = state,
                layoutContent = { error("Should not render Layout") },
                stringContent = { id -> assert(id == "stringField") },
                numberContent = { error("Should not render NumberProperty") },
                booleanContent = { error("Should not render BooleanProperty") },
            )
        }
    }

    @Test
    fun `renders number content`() {
        val schema =
            Schema(
                properties = persistentMapOf("numberField" to NumberProperty()),
            )
        val uiSchema = Control(scope = "#/properties/numberField")

        composeTestRule.setContent {
            val state = rememberJsonFormState(initialValues = mutableMapOf())
            JsonForm(
                schema = schema,
                uiSchema = uiSchema,
                state = state,
                layoutContent = { error("Should not render Layout") },
                stringContent = { error("Should not render StringProperty") },
                numberContent = { id -> assert(id == "numberField") },
                booleanContent = { error("Should not render BooleanProperty") },
            )
        }
    }

    @Test
    fun `renders boolean content`() {
        val schema =
            Schema(
                properties = persistentMapOf("booleanField" to BooleanProperty()),
            )
        val uiSchema = Control(scope = "#/properties/booleanField")

        composeTestRule.setContent {
            val state = rememberJsonFormState(initialValues = mutableMapOf())
            JsonForm(
                schema = schema,
                uiSchema = uiSchema,
                state = state,
                layoutContent = { error("Should not render Layout") },
                stringContent = { error("Should not render StringProperty") },
                numberContent = { error("Should not render NumberProperty") },
                booleanContent = { id -> assert(id == "booleanField") },
            )
        }
    }

    @Test
    fun `renders layout content`() {
        val schema =
            Schema(
                properties = persistentMapOf("stringField" to StringProperty()),
            )
        val uiSchema =
            VerticalLayout(
                elements = persistentListOf(Control(scope = "#/properties/stringField")),
            )

        composeTestRule.setContent {
            val state = rememberJsonFormState(initialValues = mutableMapOf())
            JsonForm(
                schema = schema,
                uiSchema = uiSchema,
                state = state,
                layoutContent = { assert(this.isVerticalLayout()) },
                stringContent = { id -> assert(id == "stringField") },
                numberContent = { error("Should not render NumberProperty") },
                booleanContent = { error("Should not render BooleanProperty") },
            )
        }
    }
}
