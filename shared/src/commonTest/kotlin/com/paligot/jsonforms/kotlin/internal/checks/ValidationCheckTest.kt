package com.paligot.jsonforms.kotlin.internal.checks

import com.paligot.jsonforms.kotlin.internal.FieldError
import com.paligot.jsonforms.kotlin.models.schema.ObjectProperty
import com.paligot.jsonforms.kotlin.models.schema.Schema
import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import com.paligot.jsonforms.kotlin.models.uischema.Control
import com.paligot.jsonforms.kotlin.models.uischema.VerticalLayout
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlin.test.Test
import kotlin.test.assertEquals

class ValidationCheckTest {

    @Test
    fun `check should return errors for invalid data`() {
        val schema = Schema(
            properties = persistentMapOf(
                "key1" to StringProperty(minLength = 3),
                "key2" to StringProperty()
            ),
            required = persistentListOf("key1")
        )

        val uiSchema = VerticalLayout(
            elements = persistentListOf(
                Control(scope = "#/properties/key1"),
                Control(scope = "#/properties/key2")
            )
        )

        val data = mapOf(
            "key1" to "ab",
            "key2" to ""
        )

        val errors = ValidationCheck(schema, uiSchema).check(data)

        assertEquals(1, errors.size)
        assertEquals(
            FieldError.MinLengthFieldError(3, "key1").message,
            errors.first().message
        )
    }

    @Test
    fun `check should return no errors for valid data`() {
        val schema = Schema(
            properties = persistentMapOf(
                "key1" to StringProperty(minLength = 3),
                "key2" to StringProperty()
            ),
            required = persistentListOf("key1")
        )

        val uiSchema = VerticalLayout(
            elements = persistentListOf(
                Control(scope = "#/properties/key1"),
                Control(scope = "#/properties/key2")
            )
        )

        val data = mapOf(
            "key1" to "abc",
            "key2" to "value"
        )

        val errors = ValidationCheck(schema, uiSchema).check(data)

        assertEquals(0, errors.size)
    }

    @Test
    fun `check should return errors for invalid data in nested object property`() {
        val schema = Schema(
            properties = persistentMapOf(
                "parent" to ObjectProperty(
                    properties = persistentMapOf(
                        "child" to StringProperty(minLength = 5)
                    ),
                    required = persistentListOf("child")
                )
            ),
            required = persistentListOf()
        )

        val uiSchema = VerticalLayout(
            elements = persistentListOf(
                Control(scope = "#/properties/parent/properties/child")
            )
        )

        val data = mapOf("child" to "abc")

        val errors = ValidationCheck(schema, uiSchema).check(data)

        assertEquals(1, errors.size)
        assertEquals(
            FieldError.MinLengthFieldError(5, "child").message,
            errors.first().message
        )
    }

    @Test
    fun `check should return no errors for valid data in nested object property`() {
        val schema = Schema(
            properties = persistentMapOf(
                "parent" to ObjectProperty(
                    properties = persistentMapOf(
                        "child" to StringProperty(minLength = 5)
                    ),
                    required = persistentListOf("child")
                )
            ),
            required = persistentListOf()
        )

        val uiSchema = VerticalLayout(
            elements = persistentListOf(
                Control(scope = "#/properties/parent/properties/child")
            )
        )

        val data = mapOf("child" to "valid")

        val errors = ValidationCheck(schema, uiSchema).check(data)

        assertEquals(0, errors.size)
    }

    @Test
    fun `check should return errors for missing required property in root object`() {
        val schema = Schema(
            properties = persistentMapOf(
                "key1" to StringProperty(),
                "key2" to StringProperty()
            ),
            required = persistentListOf("key1")
        )

        val uiSchema = VerticalLayout(
            elements = persistentListOf(
                Control(scope = "#/properties/key1"),
                Control(scope = "#/properties/key2")
            )
        )

        val data = mapOf("key2" to "value")

        val errors = ValidationCheck(schema, uiSchema).check(data)

        assertEquals(1, errors.size)
        assertEquals(FieldError.RequiredFieldError("key1").message, errors.first().message)
    }

    @Test
    fun `check should return errors for missing required property in nested object`() {
        val schema = Schema(
            properties = persistentMapOf(
                "parent" to ObjectProperty(
                    properties = persistentMapOf(
                        "child" to StringProperty()
                    ),
                    required = persistentListOf("child")
                )
            ),
            required = persistentListOf()
        )

        val uiSchema = VerticalLayout(
            elements = persistentListOf(
                Control(scope = "#/properties/parent/properties/child")
            )
        )

        val data = mapOf<String, Any>()

        val errors = ValidationCheck(schema, uiSchema).check(data)

        assertEquals(1, errors.size)
        assertEquals(FieldError.RequiredFieldError("child").message, errors.first().message)
    }
}
