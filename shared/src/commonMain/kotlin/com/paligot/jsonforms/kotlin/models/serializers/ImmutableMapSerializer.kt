package com.paligot.jsonforms.kotlin.models.serializers

import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class ImmutableMapSerializer<K, V>(
    keySerializer: KSerializer<K>,
    valueSerializer: KSerializer<V>,
) : KSerializer<ImmutableMap<K, V>> {
    private val delegateSerializer = MapSerializer(keySerializer, valueSerializer)

    override val descriptor = delegateSerializer.descriptor

    override fun serialize(
        encoder: Encoder,
        value: ImmutableMap<K, V>,
    ) {
        delegateSerializer.serialize(encoder, value.toMap())
    }

    override fun deserialize(decoder: Decoder): ImmutableMap<K, V> {
        return delegateSerializer.deserialize(decoder).toImmutableMap()
    }
}
