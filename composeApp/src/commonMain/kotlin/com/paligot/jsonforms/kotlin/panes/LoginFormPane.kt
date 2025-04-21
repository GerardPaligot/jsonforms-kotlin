package com.paligot.jsonforms.kotlin.panes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paligot.jsonforms.kotlin.models.schema.Schema
import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import com.paligot.jsonforms.kotlin.models.uischema.Condition
import com.paligot.jsonforms.kotlin.models.uischema.Control
import com.paligot.jsonforms.kotlin.models.uischema.Effect
import com.paligot.jsonforms.kotlin.models.uischema.Format
import com.paligot.jsonforms.kotlin.models.uischema.ControlOptions
import com.paligot.jsonforms.kotlin.models.uischema.Rule
import com.paligot.jsonforms.kotlin.models.uischema.VerticalLayout
import com.paligot.jsonforms.kotlin.ui.FormScaffold
import com.paligot.jsonforms.material3.Material3Layout
import com.paligot.jsonforms.material3.Material3StringProperty
import com.paligot.jsonforms.ui.JsonForm
import com.paligot.jsonforms.ui.rememberJsonFormState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.coroutines.launch

@Composable
fun LoginFormPane(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val state = rememberJsonFormState(initialValues = mutableMapOf())
    val schema = remember {
        Schema(
            properties = persistentMapOf(
                "email" to StringProperty(
                    pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$"
                ),
                "password" to StringProperty()
            ),
            required = persistentListOf("email", "password")
        )
    }
    val uiSchema = remember {
        VerticalLayout(
            elements = persistentListOf(
                Control(
                    scope = "#/properties/email",
                    label = "Email"
                ),
                Control(
                    scope = "#/properties/password",
                    label = "Password",
                    options = ControlOptions(format = Format.Password),
                    rule = Rule(
                        effect = Effect.Show,
                        condition = Condition(
                            scope = "#/properties/email",
                            schema = StringProperty(
                                pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$"
                            )
                        )
                    )
                )
            )
        )
    }
    FormScaffold(
        title = "Log In",
        modifier = modifier,
        onBackClick = onBackClick
    ) {
        Column(modifier = Modifier.width(500.dp)) {
            JsonForm(
                schema = schema,
                uiSchema = uiSchema,
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
                        }
                    )
                },
                numberContent = {},
                booleanContent = {}
            )
            Button(onClick = {
                scope.launch { state.validate(schema, uiSchema) }
            }) {
                Text("Validate")
            }
        }
    }
}
