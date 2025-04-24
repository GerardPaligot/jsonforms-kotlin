package com.paligot.jsonforms.kotlin.internal.queries

import com.paligot.jsonforms.kotlin.models.schema.Schema
import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import com.paligot.jsonforms.kotlin.models.uischema.Control
import com.paligot.jsonforms.kotlin.models.uischema.VerticalLayout
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ControlIsLastFieldTest {

    @Test
    fun `isLastField should return true when control is the last field in UiSchema`() {
        val lastControl = Control(scope = "#/properties/key2")
        val uiSchema = VerticalLayout(
            elements = persistentListOf(
                Control(scope = "#/properties/key1"),
                lastControl
            )
        )
        val schema = Schema(
            properties = persistentMapOf(
                "key1" to StringProperty(),
                "key2" to StringProperty()
            )
        )

        val result = lastControl.isLastField(uiSchema, schema)

        assertTrue(result)
    }

    @Test
    fun `isLastField should return false when control is not the last field in UiSchema`() {
        val firstControl = Control(scope = "#/properties/key1")
        val uiSchema = VerticalLayout(
            elements = persistentListOf(
                firstControl,
                Control(scope = "#/properties/key2")
            )
        )
        val schema = Schema(
            properties = persistentMapOf(
                "key1" to StringProperty(),
                "key2" to StringProperty()
            )
        )

        val result = firstControl.isLastField(uiSchema, schema)

        assertFalse(result)
    }

    @Test
    fun `isLastField should return false when there are intermediate string properties`() {
        val control = Control(scope = "#/properties/key1")
        val uiSchema = VerticalLayout(
            elements = persistentListOf(
                control,
                Control(scope = "#/properties/key2"),
                Control(scope = "#/properties/key3")
            )
        )
        val schema = Schema(
            properties = persistentMapOf(
                "key1" to StringProperty(),
                "key2" to StringProperty(),
                "key3" to StringProperty()
            )
        )

        val result = control.isLastField(uiSchema, schema)

        assertFalse(result)
    }

    @Test
    fun `isLastField should return true when intermediate fields are not string properties`() {
        val control = Control(scope = "#/properties/key1")
        val uiSchema = VerticalLayout(
            elements = persistentListOf(
                control,
                Control(scope = "#/properties/key2")
            )
        )
        val schema = Schema(
            properties = persistentMapOf(
                "key1" to StringProperty(),
                "key2" to StringProperty(readOnly = true)
            )
        )

        val result = control.isLastField(uiSchema, schema)

        assertTrue(result)
    }

    @Test
    fun `isLastField should return false when UiSchema is empty`() {
        val control = Control(scope = "#/properties/key")
        val uiSchema = VerticalLayout(elements = persistentListOf())
        val schema = Schema(properties = persistentMapOf("key" to StringProperty()))

        val result = control.isLastField(uiSchema, schema)

        assertFalse(result)
    }
}
