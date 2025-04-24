package com.paligot.jsonforms.kotlin.panes

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paligot.jsonforms.kotlin.models.schema.BooleanProperty
import com.paligot.jsonforms.kotlin.models.schema.Schema
import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import com.paligot.jsonforms.kotlin.models.uischema.Control
import com.paligot.jsonforms.kotlin.models.uischema.ControlOptions
import com.paligot.jsonforms.kotlin.models.uischema.Format
import com.paligot.jsonforms.kotlin.models.uischema.LayoutOptions
import com.paligot.jsonforms.kotlin.models.uischema.VerticalLayout
import com.paligot.jsonforms.kotlin.ui.FormScaffold
import com.paligot.jsonforms.material3.Material3BooleanProperty
import com.paligot.jsonforms.material3.Material3Layout
import com.paligot.jsonforms.material3.Material3NumberProperty
import com.paligot.jsonforms.material3.Material3StringProperty
import com.paligot.jsonforms.ui.JsonForm
import com.paligot.jsonforms.ui.rememberJsonFormState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.serialization.json.JsonPrimitive

@Composable
fun AccountCreationFormPane(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    val jsonFormState = rememberJsonFormState(initialValues = mutableMapOf())
    val schema =
        remember {
            Schema(
                properties =
                    persistentMapOf(
                        "name" to StringProperty(),
                        "password" to StringProperty(),
                        "gender" to
                            StringProperty(
                                enum = persistentListOf("Male", "Female", "Other"),
                            ),
                        "country" to
                            StringProperty(
                                oneOf =
                                    persistentListOf(
                                        StringProperty(const = JsonPrimitive("fr"), title = "France"),
                                        StringProperty(const = JsonPrimitive("de"), title = "Germany"),
                                        StringProperty(const = JsonPrimitive("es"), title = "Spain"),
                                    ),
                            ),
                        "consent" to BooleanProperty(),
                    ),
                required = persistentListOf("name", "password"),
            )
        }
    val uiSchema =
        remember {
            VerticalLayout(
                options = LayoutOptions(verticalSpacing = "8"),
                elements =
                    persistentListOf(
                        Control(
                            scope = "#/properties/name",
                            label = "Email",
                            options = ControlOptions(format = Format.Email),
                        ),
                        Control(
                            scope = "#/properties/password",
                            label = "Password",
                            options = ControlOptions(format = Format.Password),
                        ),
                        Control(
                            scope = "#/properties/gender",
                            label = "Gender",
                            options =
                                ControlOptions(
                                    format = Format.Radio,
                                    horizontalSpacing = "4",
                                ),
                        ),
                        Control(
                            scope = "#/properties/country",
                            label = "Country",
                        ),
                        Control(
                            scope = "#/properties/consent",
                            label = "I agree to the terms and conditions",
                            options = ControlOptions(format = Format.Toggle),
                        ),
                    ),
            )
        }
    FormScaffold(
        title = "Account creation",
        onBackClick = onBackClick,
        modifier = modifier,
    ) {
        JsonForm(
            schema = schema,
            uiSchema = uiSchema,
            modifier = Modifier.width(500.dp),
            layoutContent = { Material3Layout(content = it) },
            stringContent = { id ->
                val value = jsonFormState[id].value as String?
                val error = jsonFormState.error(id = id).value
                Material3StringProperty(
                    value = value,
                    error = error?.message,
                    onValueChange = {
                        jsonFormState[id] = it
                    },
                )
            },
            numberContent = { id ->
                val value = jsonFormState[id].value as String?
                val error = jsonFormState.error(id = id).value
                Material3NumberProperty(
                    value = value,
                    error = error?.message,
                    onValueChange = {
                        jsonFormState[id] = it
                    },
                )
            },
            booleanContent = { id ->
                val value = jsonFormState[id].value as Boolean?
                Material3BooleanProperty(
                    value = value ?: false,
                    onValueChange = {
                        jsonFormState[id] = it
                    },
                )
            },
        )
    }
}
