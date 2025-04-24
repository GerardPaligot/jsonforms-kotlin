package com.paligot.jsonforms.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.paligot.jsonforms.kotlin.internal.FieldError
import com.paligot.jsonforms.kotlin.internal.checks.ValidationCheck
import com.paligot.jsonforms.kotlin.models.schema.Schema
import com.paligot.jsonforms.kotlin.models.uischema.UiSchema
import kotlinx.coroutines.suspendCancellableCoroutine

/**
 * Create a [JsonFormState] that is remembered across compositions.
 * Changes to the provided initial values will not result in the state being recreated or changed in any way
 * if it has already been created.
 *
 * @param initialValues the initial value for fields in the form
 */
@Composable
fun rememberJsonFormState(initialValues: MutableMap<String, Any?>): JsonFormState =
    rememberSaveable(saver = JsonFormStateImpl.saver()) {
        JsonFormStateImpl(initialValues)
    }

/**
 * Create a [JsonFormState] that is remembered across compositions.
 * Changes to the provided initial values will not result in the state being recreated or changed in any way
 * if it has already been created.
 *
 * @param initialValues the initial value for fields in the form
 * @param keys  A set of inputs such that, when any of them have changed,
 * will cause the state to reset and init to be rerun
 */
@Composable
fun rememberJsonFormState(
    initialValues: MutableMap<String, Any?>,
    vararg keys: String,
): JsonFormState =
    rememberSaveable(saver = JsonFormStateImpl.saver(), inputs = keys) {
        JsonFormStateImpl(initialValues)
    }

/**
 * An object representing a form described by the standard Jsonforms.io.
 *
 * This interface is implemented by states of form containers in order to provide a low-level form api control
 * to edit the content in fields or to mark some of them in error mode.
 */
interface JsonFormState {
    /**
     * Edit value of a field. New values are observed by fields and impact directly your form.
     *
     * @param key property key
     * @param value New value for the field
     */
    operator fun set(
        key: String,
        value: Any,
    )

    /**
     * Validate if requirements and patterns described in [Schema] are respected to submit your form.
     *
     * ```kotlin
     * coroutineScope.launch {
     *     jsonFormsState.validate(schema, uiSchema)
     * }
     * ```
     *
     * @param schema Properties which can be shown on the screen.
     * @param uiSchema Form UI description of fields declared in [Schema].
     * @return true if all requirements and patterns are respected.
     */
    suspend fun validate(
        schema: Schema,
        uiSchema: UiSchema,
    ): Boolean

    /**
     * Mark a list of fields in error.
     * Note that [JsonForm] component already handle error states for you according to your requirements
     * and patterns described in your [Schema]. If you are using this function, you need to apply and handle
     * these errors by your own way.
     *
     * @param properties List of errors to display in your form.
     */
    fun markAsErrors(properties: List<FieldError>)

    /**
     * Get data displayed in your form in a [Map].
     * Note that if form values are edited after your call to this function, values won't be updated in your [Map]
     * object. If you want to observe a specific value of a control key, see [get] function which return a compose
     * [State].
     *
     * @return Form values
     */
    fun getData(): Map<String, Any?>

    /**
     * Get a specific value of a control key.
     * Note that if this field is updated after your call to this function, you won't be notified about this change.
     * If you want to observe this value, see [get] function which return a compose [State].
     *
     * @param key property key
     * @return value of the field associated to the given key
     */
    fun getValue(key: String): Any?

    /**
     * Observe the value of a specific key as a compose state to be informed by any change.
     * This function is used internally by [JsonForm] components to be updated automatically when the user type
     * or interact with your fields.
     *
     * @param key property key
     * @return state value of a field associated to the given key
     */
    @Composable
    operator fun get(key: String): State<Any?>

    /**
     * Observe the error mode of a specific key as a compose state to be informed by any mode change.
     * This function is used internally by [JsonForm] components to be updated automatically when you mark a field
     * in error or when you call [validate] function and some requirements or patterns aren't respected.
     *
     * @param id The property key
     * @return state mode of a field associated to the given key
     */
    @Composable
    fun error(id: String): State<FieldError?>
}

/**
 * A state object that can be hoisted to control and observe form fields.
 * In most cases, this will be created via [rememberJsonFormState].
 *
 * @param map the initial value for fields in the form
 */
@Stable
internal class JsonFormStateImpl(private val map: Map<String, Any?>) : JsonFormState {
    /**
     * Internal errors driven by JsonForms internal checks (requirements and patterns).
     */
    private var fieldsWithErrors by mutableStateOf<List<FieldError>>(mutableListOf())

    /**
     * Custom errors driven by the consumer. If you want to validate your form, it is
     * your business to flush these errors with [markAsErrors] function.
     */
    private var customErrors by mutableStateOf<List<FieldError>>(mutableListOf())

    /**
     * Form description with in key the id of the field and value the specification.
     */
    private val mapValues = mutableStateMapOf<String, Any?>().apply { putAll(map) }

    override operator fun set(
        key: String,
        value: Any,
    ) {
        mapValues[key] = value
    }

    override suspend fun validate(
        schema: Schema,
        uiSchema: UiSchema,
    ): Boolean {
        val validation = ValidationCheck(schema, uiSchema)
        return suspendCancellableCoroutine { continuation ->
            fieldsWithErrors = validation.check(mapValues)
            continuation.resume(fieldsWithErrors.isEmpty() && customErrors.isEmpty()) { _, _, _ ->
                fieldsWithErrors.toMutableList().clear()
            }
        }
    }

    override fun markAsErrors(properties: List<FieldError>) {
        customErrors = properties.toMutableList()
    }

    override fun getData(): Map<String, Any?> = mapValues.toMap()

    override fun getValue(key: String): Any? = mapValues[key]

    @Composable
    override operator fun get(key: String): State<Any?> = rememberUpdatedState(newValue = mapValues[key])

    @Composable
    override fun error(id: String): State<FieldError?> =
        rememberUpdatedState(newValue = (customErrors + fieldsWithErrors).find { it.scope == id })

    companion object {
        /**
         * The default [saver] implementation for [JsonFormState].
         */
        fun saver() =
            Saver<JsonFormStateImpl, Map<String, Any?>>(
                save = { HashMap(it.mapValues) },
                restore = { JsonFormStateImpl(it.toMutableMap()) },
            )
    }
}
