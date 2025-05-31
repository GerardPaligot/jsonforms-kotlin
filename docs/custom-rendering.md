This page explains how to customize the default rendering of a `JsonForm` component in 
`jsonforms-kotlin`. You can provide your own UI for specific fields or layouts by supplying custom 
composable functions to the `JsonForm` component. This allows you to tailor the form's appearance 
and behavior to your application's needs.

## Why Customize Rendering?

While the default renderers (such as Material3 or Cupertino) provide a consistent look and feel, 
you may want to:
 
* Integrate custom UI components (e.g., dropdowns, country pickers)
* Change the layout or style of certain fields
* Add special logic for user interaction

## How to Customize Rendering

You can customize rendering by providing your own implementations for the `layoutContent`, 
`stringContent`, `numberContent`, and `booleanContent` slots in the `JsonForm` composable. Each 
slot receives the necessary context (such as the field id and current value) and should emit your 
custom ui component.

**Example: Custom dropdown for country selection**

Here, the `flags` field uses a custom dropdown, while other fields use the default Material3 
renderer:

```kotlin
JsonForm(
    schema = schema,
    uiSchema = uiSchema,
    state = state,
    layoutContent = { Material3Layout(content = it) },
    stringContent = { id ->
        val value = state[id].value as String?
        val error = state.error(id = id).value
        if (id == "flags") {
            FlagDropdownField(
                value = value,
                values = values(), // your list of country codes described in the schema
                expanded = expanded,
                onFlagClick = { expanded = !expanded },
                onItemClick = { state[id] = it },
                onDismissRequest = { expanded = false },
            )
        } else {
            Material3StringProperty(
                value = value,
                error = error?.message,
                onValueChange = { state[id] = it },
            )
        }
    },
    numberContent = {},
    booleanContent = {},
)
```

* The `stringContent` lambda checks the field id. If it's `flags`, it renders a custom dropdown. Otherwise, it falls back to the default Material3 field.
* You can use this pattern to override rendering for any field or layout.

## Accessing data from schema and UI schema

When customizing rendering, the `stringContent`, `numberContent`, and `booleanContent` slots each
receive a specialized scope object: `RendererStringScope`, `RendererNumberScope`, or
`RendererBooleanScope`.

These scope interfaces provide a rich API to access metadata and configuration for the field being
rendered, by connecting the UI schema (which describes the form layout and controls) with the JSON
schema (which describes the data model and validation rules). The internal implementation of each
scope uses both schemas and the current form state to provide the following:

```kotlin
stringContent = { id ->
    val value = state[id].value as String?
    val error = state.error(id).value
    val label = this.label()
    val enabled = this.enabled()
    // ...render your custom field using this metadata...
}
```

## Tips for Customization

* You can mix and match custom and default renderers as needed.
* Use the `state` object to read and update field values and errors.
* You can provide custom logic for any field type (string, number, boolean, etc.) by implementing the corresponding content slot.

For more details, see the [usage guide](usage.md) or the [API reference](api/index.html).
