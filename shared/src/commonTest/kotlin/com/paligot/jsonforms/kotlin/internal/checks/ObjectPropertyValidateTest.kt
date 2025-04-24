package com.paligot.jsonforms.kotlin.internal.checks

import com.paligot.jsonforms.kotlin.internal.FieldError
import com.paligot.jsonforms.kotlin.models.schema.NumberProperty
import com.paligot.jsonforms.kotlin.models.schema.ObjectProperty
import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectPropertyValidateTest {
    @Test
    fun `validate should return no errors for valid data`() {
        val schema =
            ObjectProperty(
                properties =
                    persistentMapOf(
                        "name" to StringProperty(minLength = 3),
                        "age" to NumberProperty(minimum = 18),
                    ),
                required = persistentListOf("name", "age"),
            )
        val data = mapOf("name" to "John", "age" to "25")

        val result = schema.validate(data)

        assertEquals(emptyList(), result)
    }

    @Test
    fun `validate should return errors for missing required fields`() {
        val schema =
            ObjectProperty(
                properties =
                    persistentMapOf(
                        "name" to StringProperty(),
                        "age" to NumberProperty(),
                    ),
                required = persistentListOf("name", "age"),
            )
        val data = mapOf("name" to "John")

        val result = schema.validate(data).first()

        assertEquals(
            FieldError.RequiredFieldError("age").message,
            result.message,
        )
    }

    @Test
    fun `validate should return errors for invalid property values`() {
        val schema =
            ObjectProperty(
                properties =
                    persistentMapOf(
                        "name" to StringProperty(minLength = 5),
                        "age" to NumberProperty(minimum = 18),
                    ),
            )
        val data = mapOf("name" to "Joe", "age" to "15")

        val result = schema.validate(data)

        assertEquals(
            FieldError.MinLengthFieldError(5, "name").message,
            result[0].message,
        )
        assertEquals(
            FieldError.MinValueFieldError(18, "age").message,
            result[1].message,
        )
    }

    @Test
    fun `validate should return no errors for valid anyOf condition`() {
        val schema =
            ObjectProperty(
                properties = persistentMapOf(),
                anyOf =
                    persistentListOf(
                        ObjectProperty(
                            properties =
                                persistentMapOf(
                                    "type" to StringProperty(enum = persistentListOf("A")),
                                ),
                            required = persistentListOf("type"),
                        ),
                        ObjectProperty(
                            properties =
                                persistentMapOf(
                                    "type" to StringProperty(enum = persistentListOf("B")),
                                ),
                            required = persistentListOf("type"),
                        ),
                    ),
            )
        val data = mapOf("type" to "A")

        val result = schema.validate(data)

        assertEquals(emptyList(), result)
    }

    @Test
    fun `validate should return errors when no matching anyOf condition`() {
        val schema =
            ObjectProperty(
                properties = persistentMapOf(),
                anyOf =
                    persistentListOf(
                        ObjectProperty(
                            properties =
                                persistentMapOf(
                                    "type" to StringProperty(enum = persistentListOf("A")),
                                ),
                            required = persistentListOf("type"),
                        ),
                        ObjectProperty(
                            properties =
                                persistentMapOf(
                                    "type" to StringProperty(enum = persistentListOf("B")),
                                ),
                            required = persistentListOf("type"),
                        ),
                    ),
            )
        val data = mapOf("type" to "C")

        val result = schema.validate(data)

        assertEquals(
            listOf(FieldError.InvalidEnumFieldError(listOf("A"), "type").message),
            result.map { it.message },
        )
    }
}
