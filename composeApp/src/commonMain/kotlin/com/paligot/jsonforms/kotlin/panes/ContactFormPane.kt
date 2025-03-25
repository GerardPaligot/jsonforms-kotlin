package com.paligot.jsonforms.kotlin.panes

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paligot.jsonforms.kotlin.models.schema.ObjectProperty
import com.paligot.jsonforms.kotlin.models.schema.PropertyValue
import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import com.paligot.jsonforms.kotlin.models.uischema.Control
import com.paligot.jsonforms.kotlin.models.uischema.Format
import com.paligot.jsonforms.kotlin.models.uischema.HorizontalLayout
import com.paligot.jsonforms.kotlin.models.uischema.LayoutOptions
import com.paligot.jsonforms.kotlin.models.uischema.ControlOptions
import com.paligot.jsonforms.kotlin.ui.FlagDropdownField
import com.paligot.jsonforms.kotlin.ui.FormScaffold
import com.paligot.jsonforms.material3.Material3Layout
import com.paligot.jsonforms.material3.Material3StringProperty
import com.paligot.jsonforms.ui.JsonForm
import com.paligot.jsonforms.ui.rememberJsonFormState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf

@Composable
fun ContactFormPane(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    val state = rememberJsonFormState(initialValues = mutableMapOf("flags" to "fr"))
    val schema = remember {
        ObjectProperty(
            properties = persistentMapOf(
                "flags" to StringProperty(
                    oneOf = persistentListOf(
                        PropertyValue(const = "fr", title = "France"),
                        PropertyValue(const = "de", title = "Germany"),
                        PropertyValue(const = "es", title = "Spain")
                    )
                ),
                "phone" to StringProperty()
            )
        )
    }
    val uiSchema = remember {
        HorizontalLayout(
            options = LayoutOptions(horizontalSpacing = "8"),
            elements = persistentListOf(
                Control(
                    scope = "#/properties/flags",
                    label = "Country"
                ),
                Control(
                    scope = "#/properties/phone",
                    label = "Phone",
                    options = ControlOptions(format = Format.Phone)
                )
            )
        )
    }
    var expanded by remember { mutableStateOf(false) }
    FormScaffold(
        title = "Contact Form",
        modifier = modifier,
        onBackClick = onBackClick
    ) {
        JsonForm(
            modifier = Modifier.width(500.dp),
            schema = schema,
            uiSchema = uiSchema,
            state = state,
            layoutContent = { Material3Layout(content = it) },
            stringContent = { id ->
                val value = state[id].value as String?
                val error = state.error(id = id).value
                if (id == "flags") {
                    FlagDropdownField(
                        value = value,
                        values = values(),
                        expanded = expanded,
                        onFlagClick = {
                            expanded = !expanded
                        },
                        onItemClick = {
                            state[id] = it
                        },
                        onDismissRequest = {
                            expanded = false
                        }
                    )
                } else {
                    Material3StringProperty(
                        value = value,
                        error = error?.message,
                        onValueChange = {
                            state[id] = it
                        }
                    )
                }
            },
            numberContent = {},
            booleanContent = {}
        )
    }
}