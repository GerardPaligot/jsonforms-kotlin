This page demonstrates how to use the `jsonforms-kotlin` library to render dynamic forms in your 
Kotlin Multiplatform projects. It covers basic setup, state management, the main `JsonForm` 
component, and renderer usage.

## Your schemas

Before rendering a form, define your JSON Schema and UI Schema. 

> **Tip:** Both the schema and uischema models are serializable, so you can initialize them from a 
> backend service or load them dynamically at runtime.

```kotlin
val schema = Schema(
    properties = persistentMapOf(
        "email" to StringProperty(
            pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        ),
        "password" to StringProperty()
    ),
    required = persistentListOf("email", "password")
)
val uiSchema = VerticalLayout(
    elements = persistentListOf(
        Control(
            scope = "#/properties/email",
            label = "Email"
        ),
        Control(
            scope = "#/properties/password",
            label = "Password",
            options = ControlOptions(format = Format.Password)
        )
    )
)
```

## Create and manage form state

The `JsonFormState` manages your form's data, validation, and error states. You create it using the 
`rememberJsonFormState` composable, which takes a map of initial field values:

```kotlin
val formState = rememberJsonFormState(
    initialValues = mutableMapOf(
        "email" to "",
        "password" to ""
    )
)
```

> **Tip:** You can also provide an empty map (`mutableMapOf()`) to initialize the form with empty 
> values for all fields defined in your schema. The form will automatically handle the fields based 
> on your schema and uischema definitions.

You can observe and update the form state as the user interacts with the form. Validation and error 
handling are integrated for use with your schemas.

## Rendering

You can use any renderer (Material3, Cupertino, or your own custom renderer) on any platform. 
To render a form, use the `JsonForm` component and provide the appropriate layout and field 
renderers for your design system.

Here are examples for both Material3 and Cupertino renderers:

```kotlin
// Material3 rendering
JsonForm(
    schema = schema,
    uiSchema = uiSchema,
    state = formState,
    layoutContent = { Material3Layout(content = it) },
    stringContent = { id ->
        val value = formState[id].value as String?
        val error = formState.error(id = id).value
        Material3StringProperty(
            value = value,
            error = error?.message,
            onValueChange = { formState[id] = it }
        )
    },
    // Add numberContent, booleanContent, etc. as needed
)

// Cupertino rendering
JsonForm(
    schema = schema,
    uiSchema = uiSchema,
    state = formState,
    layoutContent = { CupertinoLayout(content = it) },
    stringContent = { id ->
        val value = formState[id].value as String?
        val error = formState.error(id = id).value
        CupertinoStringProperty(
            value = value,
            error = error?.message,
            onValueChange = { formState[id] = it }
        )
    },
    // Add numberContent, booleanContent, etc. as needed
)
```

You can mix and match renderers or create your own by providing custom implementations for the 
layout and field content slots. All renderers are available on all supported platforms.

## Accessing form data

To access the current data entered in the form, use the `getData()` method on your form state. 
This returns a map where each key is a field name and the value is the current user input for that 
field:

```kotlin
val data: Map<String, Any?> = formState.getData()
```

You can call this method at any time to retrieve the latest values from the form. This is useful 
for submitting the form data to a backend or for further processing in your application.

## Validating form data

To validate the form content according to the rules described in your schema and uischema, use 
the `validate` suspend function on your form state. This function checks all requirements 
(such as required fields, patterns, and other constraints) and updates the error state for each 
field:

```kotlin
import kotlinx.coroutines.launch

val scope = rememberCoroutineScope()

Button(onClick = {
    scope.launch {
        val isValid = formState.validate(schema, uiSchema)
        // isValid is true if all requirements and patterns are respected
        // You can use this to control form submission
    }
}) {
    Text("Validate")
}
```

* The `validate` function is suspendable and should be called from a coroutine (as shown above).
* After calling `validate`, the form will update its error states, and you can access error messages for each field using `formState.error(id).value`.
* The function returns `true` if the form is valid, or `false` if there are validation errors.

This approach ensures that your form always respects the schema and uischema rules before 
submission or further processing.
