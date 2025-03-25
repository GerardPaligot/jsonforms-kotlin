package com.paligot.jsonforms.kotlin.models.serializers

import com.paligot.jsonforms.kotlin.models.schema.ObjectProperty
import com.paligot.jsonforms.kotlin.models.schema.Property
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class ObjectPropertyListSerializer : JsonContentPolymorphicSerializer<List<ObjectProperty>>(
    List::class as KClass<List<ObjectProperty>>
) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out List<ObjectProperty>> {
        return if (element is JsonArray) {
            ListSerializer(ObjectProperty.serializer())
        } else {
            SingleObjectPropertyListSerializer()
        }
    }

    class SingleObjectPropertyListSerializer : KSerializer<List<ObjectProperty>> {
        override val descriptor: SerialDescriptor
            get() = Property.serializer().descriptor

        override fun deserialize(decoder: Decoder): List<ObjectProperty> {
            return listOf(ObjectProperty.serializer().deserialize(decoder))
        }

        override fun serialize(encoder: Encoder, value: List<ObjectProperty>) {
            throw Exception("Not in use")
        }
    }
}
