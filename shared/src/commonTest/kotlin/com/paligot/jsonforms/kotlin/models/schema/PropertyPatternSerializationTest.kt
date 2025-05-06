package com.paligot.jsonforms.kotlin.models.schema

import kotlinx.collections.immutable.persistentMapOf
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class PropertyPatternSerializationTest {
    private val json = Json { encodeDefaults = true }

    @Test
    fun `should serialize and deserialize pattern in BooleanProperty`() {
        val property = BooleanProperty(pattern = "true|false".toRegex())
        val serialized = json.encodeToString(BooleanProperty.serializer(), property)
        val deserialized = json.decodeFromString(BooleanProperty.serializer(), serialized)

        assertEquals(property.pattern!!.pattern, deserialized.pattern!!.pattern)
    }

    @Test
    fun `should serialize and deserialize pattern in StringProperty`() {
        val property = StringProperty(pattern = "[a-zA-Z]+".toRegex())
        val serialized = json.encodeToString(StringProperty.serializer(), property)
        val deserialized = json.decodeFromString(StringProperty.serializer(), serialized)

        assertEquals(property.pattern!!.pattern, deserialized.pattern!!.pattern)
    }

    @Test
    fun `should serialize and deserialize pattern in ObjectProperty`() {
        val property =
            ObjectProperty(
                properties = persistentMapOf(),
                pattern = "\\d+".toRegex(),
            )
        val serialized = json.encodeToString(ObjectProperty.serializer(), property)
        val deserialized = json.decodeFromString(ObjectProperty.serializer(), serialized)

        assertEquals(property.pattern!!.pattern, deserialized.pattern!!.pattern)
    }

    @Test
    fun `should serialize and deserialize pattern in ArrayProperty`() {
        val property = ArrayProperty(pattern = ".*".toRegex())
        val serialized = json.encodeToString(ArrayProperty.serializer(), property)
        val deserialized = json.decodeFromString(ArrayProperty.serializer(), serialized)

        assertEquals(property.pattern!!.pattern, deserialized.pattern!!.pattern)
    }
}
