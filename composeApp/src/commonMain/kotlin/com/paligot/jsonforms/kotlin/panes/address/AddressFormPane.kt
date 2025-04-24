package com.paligot.jsonforms.kotlin.panes.address

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paligot.jsonforms.kotlin.ui.FormScaffold
import com.paligot.jsonforms.material3.Material3Layout
import com.paligot.jsonforms.material3.Material3StringProperty
import com.paligot.jsonforms.ui.JsonForm
import com.paligot.jsonforms.ui.rememberJsonFormState

@Composable
fun AddressFormPane(
    uiModel: AddressUiModel,
    modifier: Modifier = Modifier,
    onStreetChange: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    val state = rememberJsonFormState(initialValues = mutableMapOf())
    LaunchedEffect(uiModel.generatedAddress) {
        state["street"] = uiModel.generatedAddress?.street ?: ""
        state["city"] = uiModel.generatedAddress?.city ?: ""
        state["country"] = uiModel.generatedAddress?.country ?: ""
    }
    FormScaffold(
        title = "Address form",
        modifier = modifier,
        onBackClick = onBackClick,
    ) {
        JsonForm(
            modifier = Modifier.width(500.dp),
            schema = uiModel.schema,
            uiSchema = uiModel.uiSchema,
            state = state,
            layoutContent = { Material3Layout(content = it) },
            stringContent = { id ->
                val value = state[id].value as String?
                val error = state.error(id = id).value
                Material3StringProperty(
                    value = value,
                    error = error?.message,
                    onValueChange = {
                        state[id] = it
                        onStreetChange(it)
                    },
                )
            },
            numberContent = {},
            booleanContent = {},
        )
    }
}
