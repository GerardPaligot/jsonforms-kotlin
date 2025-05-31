State management is central to building dynamic forms with `jsonforms-kotlin`. The library provides 
composable functions to create and remember form state, as well as a rich API to interact with and 
observe form data, validation, and errors.

## Creating and remembering form state

There are two main functions to create and remember form state:

`rememberJsonFormState(initialValues: MutableMap<String, Any?>)`

Creates a `JsonFormState` instance that persists across recompositions. The `initialValues` map 
sets the starting values for each field.

`rememberJsonFormState(initialValues: MutableMap<String, Any?>, vararg keys: String)`

Same as above, but the state will reset if any of the provided keys change. Useful for dynamic 
forms or when you want to reset the form on certain changes.

Both functions use Compose's `rememberSaveable` for state persistence.

**Example:**
```kotlin
val formState = rememberJsonFormState(mutableMapOf("email" to "", "password" to ""))
// or with reset keys:
val formState = rememberJsonFormState(mutableMapOf(), "email", "password")
```

## JsonFormState interface: usage and examples

The `JsonFormState` interface provides a set of functions to manage and observe your form's data, 
validation, and errors. Here are the main functions, with usage examples and a short description 
for each:

**Initialize state**

```kotlin
val formState = rememberJsonFormState(mutableMapOf("email" to "", "password" to ""))
```

**Set a field value**

Set the value of a field:
```kotlin
formState["email"] = "user@example.com"
```

**Get a field value**

Get the value of a specific field:
```kotlin
val email = formState.getValue("email")
```

**Observe field value**

Observe a field's value as Compose state (reactively updates UI):
```kotlin
val emailState: State<Any?> = formState["email"]
```

**Observe field error**

Observe a field's error state as Compose state:
```kotlin
val emailError: State<FieldError?> = formState.error("email")
```

**Validate form**

Validate the form against your schema and uischema. Returns `true` if valid, `false` otherwise. 
Updates error states for each field:
```kotlin
val isValid = formState.validate(schema, uiSchema)
```

**Get all data**

Get a snapshot of all form data as a map:
```kotlin
val data = formState.getData()
```

**Mark custom errors**

Manually mark fields as having errors (useful for custom validation scenarios):
```kotlin
formState.markAsErrors(listOf(FieldError(scope = "#/properties/email", message = "Invalid email")))
```

For more details, see the [API reference](api/index.html) or the [usage guide](usage.md).
