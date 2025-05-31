# Cupertino Renderer

This module provides a Cupertino-style renderer. It enables you to render dynamic forms using 
Cupertino (Apple-like) components, fully compatible with Kotlin Multiplatform and Compose 
Multiplatform projects.

## Usage

Add the dependency to your project:

```kotlin
dependencies {
    implementation("com.paligot.jsonforms.kotlin:cupertino:<version>")
}
```

In your Compose code, use the Cupertino renderer functions in the `JsonForm` component:

```kotlin
import com.paligot.jsonforms.cupertino.*

JsonForm(
    schema = schema,
    uiSchema = uiSchema,
    state = formState,
    layoutContent = { CupertinoLayout(content = it) },
    stringContent = { scope ->
        CupertinoStringProperty(
            value = formState[scope.id].value as String?,
            error = formState.error(scope.id).value,
            onValueChange = { formState[scope.id] = it }
        )
    },
    numberContent = { scope ->
        CupertinoNumberProperty(
            value = formState[scope.id].value as String?,
            error = formState.error(scope.id).value,
            onValueChange = { formState[scope.id] = it }
        )
    },
    booleanContent = { scope ->
        CupertinoBooleanProperty(
            value = formState[scope.id].value as Boolean? ?: false,
            onValueChange = { formState[scope.id] = it }
        )
    }
)
```

## Reference

- [Usage guide](../../docs/usage.md)
- [Custom rendering guide](../../docs/custom-rendering.md)
- [Create a renderer guide](../../docs/create-renderer.md)
