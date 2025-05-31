# Shared

This module contains the core models, schema definitions, and utilities. It is designed to be used 
across all platforms and renderer modules, providing the foundation for dynamic form generation and 
validation.

## Usage

Add the dependency to your project:

```kotlin
dependencies {
    implementation("com.paligot.jsonforms.kotlin:shared:<version>")
}
```

Import and use the schema and UI schema models in your code:

```kotlin
import com.paligot.jsonforms.kotlin.models.schema.*
import com.paligot.jsonforms.kotlin.models.uischema.*

val schema = Schema(
    properties = persistentMapOf(
        "email" to StringProperty(pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"),
        "password" to StringProperty()
    ),
    required = persistentListOf("email", "password")
)

val uiSchema = VerticalLayout(
    elements = persistentListOf(
        Control(scope = "#/properties/email", label = "Email"),
        Control(scope = "#/properties/password", label = "Password")
    )
)
```
