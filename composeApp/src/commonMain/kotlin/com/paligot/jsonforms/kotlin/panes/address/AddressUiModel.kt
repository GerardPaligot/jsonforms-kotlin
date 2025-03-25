package com.paligot.jsonforms.kotlin.panes.address

import com.paligot.jsonforms.kotlin.models.schema.Schema
import com.paligot.jsonforms.kotlin.models.uischema.UiSchema

data class GeneratedAddressUiModel(
    val street: String,
    val city: String,
    val country: String
)

data class AddressUiModel(
    val schema: Schema,
    val uiSchema: UiSchema,
    val generatedAddress: GeneratedAddressUiModel? = null
)
