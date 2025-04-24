package com.paligot.jsonforms.kotlin.models.schema

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import kotlin.test.Test
import kotlin.test.assertEquals

class SchemaTest {
    private val json = Json { encodeDefaults = true }

    @Test
    fun `test StringProperty serialization`() {
        val stringProperty =
            StringProperty(
                title = "Name",
                format = "text",
                description = "A name field",
                minLength = 3,
                maxLength = 50,
                enum = persistentListOf("Alice", "Bob"),
                oneOf =
                    persistentListOf(
                        StringProperty(title = "Alice", const = JsonPrimitive("Alice")),
                        StringProperty(title = "Bob", const = JsonPrimitive("Bob")),
                    ),
            )
        val serialized = json.encodeToString(Property.serializer(), stringProperty)
        val deserialized = json.decodeFromString(Property.serializer(), serialized)

        assertEquals(stringProperty, deserialized)
    }

    @Test
    fun `test NumberProperty serialization`() {
        val numberProperty =
            NumberProperty(
                title = "Age",
                description = "An age field",
                minimum = 0,
                maximum = 120,
                default = 25,
            )
        val serialized = json.encodeToString(Property.serializer(), numberProperty)
        val deserialized = json.decodeFromString(Property.serializer(), serialized)

        assertEquals(numberProperty, deserialized)
    }

    @Test
    fun `test BooleanProperty serialization`() {
        val booleanProperty =
            BooleanProperty(
                title = "Is Active",
                description = "A boolean field",
            )
        val serialized = json.encodeToString(Property.serializer(), booleanProperty)
        val deserialized = json.decodeFromString(Property.serializer(), serialized)

        assertEquals(booleanProperty, deserialized)
    }

    @Test
    fun `test ObjectProperty serialization`() {
        val objectProperty =
            ObjectProperty(
                title = "User",
                properties =
                    persistentMapOf(
                        "name" to StringProperty(title = "Name"),
                        "age" to NumberProperty(title = "Age"),
                        "parent" to
                            ObjectProperty(
                                title = "Parent",
                                properties =
                                    persistentMapOf(
                                        "name" to StringProperty(title = "Name"),
                                    ),
                            ),
                    ),
                required = persistentListOf("name"),
            )
        val serialized = json.encodeToString(Property.serializer(), objectProperty)
        val deserialized = json.decodeFromString(Property.serializer(), serialized)

        assertEquals(objectProperty, deserialized)
    }

    @Test
    fun `test ArrayProperty serialization`() {
        val arrayProperty =
            ArrayProperty(
                title = "Tags",
                items = StringProperty(title = "Tag"),
                uniqueItems = true,
            )
        val serialized = json.encodeToString(Property.serializer(), arrayProperty)
        val deserialized = json.decodeFromString(Property.serializer(), serialized)

        assertEquals(arrayProperty, deserialized)
    }
}
