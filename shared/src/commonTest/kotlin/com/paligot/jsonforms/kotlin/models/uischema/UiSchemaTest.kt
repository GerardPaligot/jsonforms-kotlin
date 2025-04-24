package com.paligot.jsonforms.kotlin.models.uischema

import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class UiSchemaTest {
    private val json = Json { encodeDefaults = true }

    @Test
    fun `test VerticalLayout serialization`() {
        val verticalLayout =
            VerticalLayout(
                elements =
                    persistentListOf(
                        Control(scope = "name", label = "Name"),
                        Control(scope = "age", label = "Age"),
                    ),
            )
        val serialized = json.encodeToString(UiSchema.serializer(), verticalLayout)
        val deserialized = json.decodeFromString(UiSchema.serializer(), serialized)

        assertEquals(verticalLayout, deserialized)
    }

    @Test
    fun `test HorizontalLayout serialization`() {
        val horizontalLayout =
            HorizontalLayout(
                elements =
                    persistentListOf(
                        Control(scope = "email", label = "Email"),
                        Control(scope = "phone", label = "Phone"),
                    ),
            )
        val serialized = json.encodeToString(UiSchema.serializer(), horizontalLayout)
        val deserialized = json.decodeFromString(UiSchema.serializer(), serialized)

        assertEquals(horizontalLayout, deserialized)
    }

    @Test
    fun `test GroupLayout serialization`() {
        val groupLayout =
            GroupLayout(
                label = "User Info",
                elements =
                    persistentListOf(
                        Control(scope = "username", label = "Username"),
                        Control(scope = "password", label = "Password"),
                    ),
            )
        val serialized = json.encodeToString(UiSchema.serializer(), groupLayout)
        val deserialized = json.decodeFromString(UiSchema.serializer(), serialized)

        assertEquals(groupLayout, deserialized)
    }

    @Test
    fun `test Control serialization`() {
        val control =
            Control(
                scope = "address",
                label = "Address",
                options = ControlOptions(format = Format.Email, readOnly = true),
            )
        val serialized = json.encodeToString(UiSchema.serializer(), control)
        val deserialized = json.decodeFromString(UiSchema.serializer(), serialized)

        assertEquals(control, deserialized)
    }
}
