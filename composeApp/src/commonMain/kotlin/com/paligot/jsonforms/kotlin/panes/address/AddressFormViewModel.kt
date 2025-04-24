package com.paligot.jsonforms.kotlin.panes.address

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paligot.jsonforms.kotlin.models.schema.Schema
import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import com.paligot.jsonforms.kotlin.models.uischema.Control
import com.paligot.jsonforms.kotlin.models.uischema.VerticalLayout
import com.paligot.jsonforms.kotlin.panes.address.geocode.GeocodeApi
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddressFormViewModel() : ViewModel() {
    private val geocodeApi = GeocodeApi.create(true)
    private val schema =
        Schema(
            properties =
                persistentMapOf(
                    "search" to StringProperty(),
                    "street" to StringProperty(readOnly = true),
                    "city" to StringProperty(readOnly = true),
                    "country" to StringProperty(readOnly = true),
                ),
        )
    private val uiSchema =
        VerticalLayout(
            elements =
                persistentListOf(
                    Control(
                        scope = "#/properties/search",
                        label = "Search",
                    ),
                    Control(
                        scope = "#/properties/street",
                        label = "Street",
                    ),
                    Control(
                        scope = "#/properties/city",
                        label = "City",
                    ),
                    Control(
                        scope = "#/properties/country",
                        label = "Country",
                    ),
                ),
        )
    private val queryFlow = MutableStateFlow<String?>(null)

    @OptIn(FlowPreview::class)
    val uiState: StateFlow<AddressUiModel> =
        queryFlow
            .debounce(timeoutMillis = 250)
            .distinctUntilChanged()
            .map { query ->
                if (query == null) {
                    return@map AddressUiModel(schema, uiSchema)
                }
                val geocode = geocodeApi.geocode(query)
                val address =
                    geocode.results
                        .fold(mutableListOf<GeneratedAddressUiModel>()) { acc, result ->
                            val streetNumber =
                                result.addressComponents
                                    .find { address -> address.types.contains("street_number") }
                            val route =
                                result.addressComponents
                                    .find { address -> address.types.contains("route") }
                            val locality =
                                result.addressComponents
                                    .find { address -> address.types.contains("locality") }
                            val country =
                                result.addressComponents
                                    .find { address -> address.types.contains("country") }
                            if (locality != null && country != null) {
                                acc.add(
                                    GeneratedAddressUiModel(
                                        street = "${streetNumber?.longName ?: ""} ${route?.longName ?: ""}",
                                        city = locality.longName,
                                        country = country.longName,
                                    ),
                                )
                            }
                            acc
                        }
                        .firstOrNull()
                AddressUiModel(schema = schema, uiSchema = uiSchema, generatedAddress = address)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = AddressUiModel(schema, uiSchema),
            )

    fun queryChange(streetName: String) =
        viewModelScope.launch {
            queryFlow.update { streetName }
        }
}
