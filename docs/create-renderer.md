This page explains how to create your own renderer for `jsonforms-kotlin`, allowing you to fully 
customize the look and feel of forms to match your internal design system or branding. This is 
useful if the provided renderers do not meet your requirements.

## What is a renderer?

A renderer is a set of composable functions that define how each form field and layout should be 
displayed. You implement these by providing composable extensions for the various scope interfaces: 
`RendererStringScope`, `RendererNumberScope`, `RendererBooleanScope`, and `RendererLayoutScope`.

## Steps to create a custom renderer

**Implement field renderers**: Create composable extension functions for each field type and use the scope API to access field metadata, options, and state.

* `RendererStringScope.YourStringProperty(...)`
* `RendererNumberScope.YourNumberProperty(...)`
* `RendererBooleanScope.YourBooleanProperty(...)`

**Implement layout renderer**: Create a composable extension for `RendererLayoutScope` 
(e.g., `YourLayout(...)`) to control how groups of fields are arranged (vertical, horizontal, etc).

**Use your Renderer in JsonForm**: Pass your custom composables to the `JsonForm` component's 
`layoutContent`, `stringContent`, `numberContent`, and `booleanContent` slots.

**Example: Minimal custom renderer**

```kotlin
@Composable
fun RendererStringScope.MyStringProperty(
    value: String?,
    error: String? = null,
    onValueChange: (String) -> Unit,
) {
    // Use scope methods for label, enabled, etc.
    MyTextField(
        value = value ?: "",
        label = label(),
        enabled = enabled(),
        error = error,
        onValueChange = onValueChange
    )
}

@Composable
fun RendererNumberScope.MyNumberProperty(
    value: String?,
    error: String? = null,
    onValueChange: (String) -> Unit,
) {
    MyNumberField(
        value = value ?: "",
        label = label(),
        enabled = enabled(),
        error = error,
        onValueChange = onValueChange
    )
}

@Composable
fun RendererBooleanScope.MyBooleanProperty(
    value: Boolean,
    onValueChange: (Boolean) -> Unit,
) {
    MySwitch(
        checked = value,
        label = label(),
        enabled = enabled(),
        onCheckedChange = onValueChange
    )
}

@Composable
fun RendererLayoutScope.MyLayout(
    content: @Composable (UiSchema) -> Unit
) {
    Column {
        elements().forEach { child ->
            content(child)
        }
    }
}
```

## Using your renderer

```kotlin
JsonForm(
    schema = schema,
    uiSchema = uiSchema,
    state = state,
    layoutContent = { MyLayout(content = it) },
    stringContent = { scope ->
        MyStringProperty(
            value = state[scope.id].value as String?,
            error = state.error(scope.id).value,
            onValueChange = { state[scope.id] = it }
        )
    },
    numberContent = { scope ->
        MyNumberProperty(
            value = state[scope.id].value as String?,
            error = state.error(scope.id).value,
            onValueChange = { state[scope.id] = it }
        )
    },
    booleanContent = { scope ->
        MyBooleanProperty(
            value = state[scope.id].value as Boolean? ?: false,
            onValueChange = { state[scope.id] = it }
        )
    }
)
```

For more details, see the [API reference](api/index.html) and the source code of the 
material3 and cupertino modules.
