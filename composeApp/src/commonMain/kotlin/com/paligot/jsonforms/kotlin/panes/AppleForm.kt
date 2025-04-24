package com.paligot.jsonforms.kotlin.panes

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paligot.jsonforms.cupertino.CupertinoBooleanProperty
import com.paligot.jsonforms.cupertino.CupertinoLayout
import com.paligot.jsonforms.cupertino.CupertinoNumberProperty
import com.paligot.jsonforms.cupertino.CupertinoStringProperty
import com.paligot.jsonforms.kotlin.models.schema.BooleanProperty
import com.paligot.jsonforms.kotlin.models.schema.Schema
import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import com.paligot.jsonforms.kotlin.models.uischema.Control
import com.paligot.jsonforms.kotlin.models.uischema.ControlOptions
import com.paligot.jsonforms.kotlin.models.uischema.Format
import com.paligot.jsonforms.kotlin.models.uischema.GroupLayout
import com.paligot.jsonforms.kotlin.models.uischema.LayoutOptions
import com.paligot.jsonforms.kotlin.models.uischema.VerticalLayout
import com.paligot.jsonforms.ui.JsonForm
import com.paligot.jsonforms.ui.rememberJsonFormState
import com.slapps.cupertino.CupertinoNavigateBackButton
import com.slapps.cupertino.CupertinoScaffold
import com.slapps.cupertino.CupertinoText
import com.slapps.cupertino.CupertinoTopAppBar
import com.slapps.cupertino.ExperimentalCupertinoApi
import com.slapps.cupertino.theme.CupertinoTheme
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.serialization.json.JsonPrimitive

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun AppleForm(
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
                                        StringProperty(const = JsonPrimitive("it"), title = "Italy"),
                                        StringProperty(const = JsonPrimitive("be"), title = "Belgium"),
                                        StringProperty(const = JsonPrimitive("uk"), title = "United Kingdom"),
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
                options = LayoutOptions(verticalSpacing = "16"),
                elements =
                    persistentListOf(
                        GroupLayout(
                            label = "Account",
                            elements =
                                persistentListOf(
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
                                        scope = "#/properties/name",
                                        label = "Email",
                                        options = ControlOptions(format = Format.Email),
                                    ),
                                    Control(
                                        scope = "#/properties/password",
                                        label = "Password",
                                        options = ControlOptions(format = Format.Password),
                                    ),
                                ),
                        ),
                        GroupLayout(
                            label = "Additional information",
                            elements =
                                persistentListOf(
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
                        ),
                    ),
            )
        }
    CupertinoTheme {
        CupertinoScaffold(
            modifier = modifier,
            topBar = {
                CupertinoTopAppBar(
                    title = { CupertinoText("Apple Form") },
                    navigationIcon = {
                        CupertinoNavigateBackButton(onClick = onBackClick) {
                            CupertinoText(text = "Back")
                        }
                    },
                )
            },
            containerColor = CupertinoTheme.colorScheme.systemGroupedBackground,
            content = {
                LazyColumn(
                    modifier = Modifier.width(500.dp),
                    contentPadding = it,
                ) {
                    item {
                        JsonForm(
                            modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
                            schema = schema,
                            uiSchema = uiSchema,
                            layoutContent = { CupertinoLayout(content = it) },
                            stringContent = { id ->
                                val value = jsonFormState[id].value as String?
                                val error = jsonFormState.error(id = id).value
                                CupertinoStringProperty(
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
                                CupertinoNumberProperty(
                                    value = value,
                                    error = error?.message,
                                    onValueChange = {
                                        jsonFormState[id] = it
                                    },
                                )
                            },
                            booleanContent = { id ->
                                val value = jsonFormState[id].value as Boolean?
                                CupertinoBooleanProperty(
                                    value = value ?: false,
                                    onValueChange = {
                                        jsonFormState[id] = it
                                    },
                                )
                            },
                        )
                    }
                }
            },
        )
    }
}
