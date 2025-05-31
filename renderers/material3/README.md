# Material3 Renderer

This module provides a Material Design 3 (Material You) renderer. It enables you to render dynamic 
forms using Material3 components, fully compatible with Kotlin Multiplatform and Compose 
Multiplatform projects.

## Usage

Add the dependency to your project:

```kotlin
dependencies {
    implementation("com.paligot.jsonforms.kotlin:material3:<version>")
}
```

In your Compose code, use the Material3 renderer functions in the `JsonForm` component:

```kotlin
import com.paligot.jsonforms.material3.*

JsonForm(
    schema = schema,
    uiSchema = uiSchema,
    state = formState,
    layoutContent = { Material3Layout(content = it) },
    stringContent = { scope ->
        Material3StringProperty(
            value = formState[scope.id].value as String?,
            error = formState.error(scope.id).value,
            onValueChange = { formState[scope.id] = it }
        )
    },
    numberContent = { scope ->
        Material3NumberProperty(
            value = formState[scope.id].value as String?,
            error = formState.error(scope.id).value,
            onValueChange = { formState[scope.id] = it }
        )
    },
    booleanContent = { scope ->
        Material3BooleanProperty(
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
