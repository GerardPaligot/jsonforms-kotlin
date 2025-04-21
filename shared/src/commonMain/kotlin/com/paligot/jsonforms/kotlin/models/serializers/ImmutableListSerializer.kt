package com.paligot.jsonforms.kotlin.models.serializers

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class ImmutableListSerializer<T>(elementSerializer: KSerializer<T>) : KSerializer<ImmutableList<T>> {
    private val delegateSerializer = ListSerializer(elementSerializer)

    override val descriptor = delegateSerializer.descriptor

    override fun serialize(encoder: Encoder, value: ImmutableList<T>) {
        delegateSerializer.serialize(encoder, value.toList())
    }

    override fun deserialize(decoder: Decoder): ImmutableList<T> {
        return delegateSerializer.deserialize(decoder).toImmutableList()
    }
}
