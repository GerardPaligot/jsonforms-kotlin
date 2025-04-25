package com.paligot.jsonforms.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.paligot.jsonforms.kotlin.SchemaProvider
import com.paligot.jsonforms.kotlin.SchemaProviderImpl
import com.paligot.jsonforms.kotlin.models.schema.Schema
import com.paligot.jsonforms.kotlin.models.uischema.UiSchema

/**
 * Display a form described in a [Schema] and [UiSchema].
 * If you want to interact with the form, you can declare your own state before the declaration of this component
 * and pass it as parameter. You'll be able to interact with fields of the form.
 * If you want to override a specific field of your form, you can add a custom render in the sniper parameter.
 *
 * ```kotlin
 * val schema = Schema(
 *     properties = mutableMapOf("email" to StringProperty()),
 *     required = arrayListOf("email")
 * )
 * val uiSchema = vertical {
 *     control("#/properties/email") {
 *         label = "Email"
 *     }
 * }
 * val jsonFormsState = rememberJsonFormState(initialValues = mutableMapOf())
 * JsonForm(
 *     schema = schema,
 *     uiSchema = uiSchema,
 *     state = jsonFormsState
 * )
 * ```
 *
 * @param schema Properties which can be shown on the screen.
 * @param uiSchema Form UI description of fields declared in [Schema].
 * @param modifier The [Modifier] applied at the root level of the form.
 * @param state State of the form to interact with fields inside.
 */
@Composable
fun JsonForm(
    schema: Schema,
    uiSchema: UiSchema,
    modifier: Modifier = Modifier,
    state: JsonFormState = rememberJsonFormState(initialValues = mutableMapOf()),
    layoutContent: @Composable (RendererLayoutScope.(@Composable (UiSchema) -> Unit) -> Unit),
    stringContent: @Composable (RendererStringScope.(id: String) -> Unit),
    numberContent: @Composable (RendererNumberScope.(id: String) -> Unit),
    booleanContent: @Composable (RendererBooleanScope.(id: String) -> Unit),
) {
    val schemeProvider = rememberSchemeProvider(uiSchema = uiSchema, schema = schema)
    Box(modifier = modifier) {
        Layout(
            uiSchema = uiSchema,
            jsonFormState = state,
            layoutContent = layoutContent,
            content = { control ->
                Property(
                    control = control,
                    schemaProvider = schemeProvider,
                    jsonFormState = state,
                    stringContent = stringContent,
                    numberContent = numberContent,
                    booleanContent = booleanContent,
                )
            },
        )
    }
}

@Composable
internal fun rememberSchemeProvider(
    uiSchema: UiSchema,
    schema: Schema,
): SchemaProvider =
    remember(uiSchema, schema) {
        SchemaProviderImpl(uiSchema, schema)
    }
