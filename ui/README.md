# UI

This module provides the core UI logic and composable components. It is responsible for form state 
management, the main `JsonForm` component, and the interfaces and scopes that enable custom and 
platform-specific renderers.

## Usage

Add the dependency to your project:

```kotlin
dependencies {
    implementation("com.paligot.jsonforms.kotlin:ui:<version>")
}
```

Import and use the main UI components in your code:

```kotlin
import com.paligot.jsonforms.ui.*

val formState = rememberJsonFormState(mutableMapOf("email" to "", "password" to ""))

JsonForm(
    schema = schema,
    uiSchema = uiSchema,
    state = formState,
    layoutContent = { /* your layout renderer */ },
    stringContent = { /* your string field renderer */ },
    numberContent = { /* your number field renderer */ },
    booleanContent = { /* your boolean field renderer */ }
)
```
