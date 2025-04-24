package com.paligot.jsonforms.kotlin.internal.queries

import com.paligot.jsonforms.kotlin.models.schema.ObjectProperty
import com.paligot.jsonforms.kotlin.models.schema.Schema
import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import com.paligot.jsonforms.kotlin.models.uischema.Control
import kotlinx.collections.immutable.persistentMapOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ObjectPropertyGetPropertyByControlTest {
    @Test
    fun `getPropertyByControl should return the correct property when the path is valid`() {
        val schema =
            Schema(
                properties = persistentMapOf("key" to StringProperty()),
            )
        val control = Control(scope = "#/properties/key")

        val result = schema.getPropertyByControl<StringProperty>(control)

        assertEquals(StringProperty::class, result::class)
    }

    @Test
    fun `getPropertyByControl should throw an error when the property does not exist`() {
        val schema =
            Schema(
                properties = persistentMapOf("key" to StringProperty()),
            )
        val control = Control(scope = "#/properties/nonexistent")

        assertFailsWith<IllegalStateException> {
            schema.getPropertyByControl<StringProperty>(control)
        }
    }

    @Test
    fun `getPropertyByControl should throw an error when the last key is an object`() {
        val schema =
            Schema(
                properties =
                    persistentMapOf(
                        "key" to ObjectProperty(properties = persistentMapOf("subKey" to StringProperty())),
                    ),
            )
        val control = Control(scope = "#/properties/key")

        assertFailsWith<IllegalStateException> {
            schema.getPropertyByControl<ObjectProperty>(control)
        }
    }

    @Test
    fun `getPropertyByControl should throw an error when an intermediate key is not an object`() {
        val schema =
            Schema(
                properties = persistentMapOf("key" to StringProperty()),
            )
        val control = Control(scope = "#/properties/key/properties/subKey")

        assertFailsWith<IllegalStateException> {
            schema.getPropertyByControl<StringProperty>(control)
        }
    }

    @Test
    fun `getPropertyByControl should return the correct nested property`() {
        val schema =
            Schema(
                properties =
                    persistentMapOf(
                        "key" to
                            ObjectProperty(
                                properties = persistentMapOf("subKey" to StringProperty()),
                            ),
                    ),
            )
        val control = Control(scope = "#/properties/key/properties/subKey")

        val result = schema.getPropertyByControl<StringProperty>(control)

        assertEquals(StringProperty::class, result::class)
    }
}
