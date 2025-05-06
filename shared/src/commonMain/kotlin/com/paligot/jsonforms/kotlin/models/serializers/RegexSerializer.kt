package com.paligot.jsonforms.kotlin.models.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class RegexSerializer : KSerializer<Regex> {
    private val delegateSerializer = String.serializer()

    override val descriptor: SerialDescriptor = delegateSerializer.descriptor

    override fun serialize(
        encoder: Encoder,
        value: Regex,
    ) {
        delegateSerializer.serialize(encoder, value.pattern)
    }

    override fun deserialize(decoder: Decoder): Regex = delegateSerializer.deserialize(decoder).toRegex()
}
