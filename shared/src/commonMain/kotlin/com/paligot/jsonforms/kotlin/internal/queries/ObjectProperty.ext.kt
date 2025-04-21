package com.paligot.jsonforms.kotlin.internal.queries

import com.paligot.jsonforms.kotlin.internal.ext.propertyPath
import com.paligot.jsonforms.kotlin.models.schema.ObjectProperty
import com.paligot.jsonforms.kotlin.models.schema.Property
import com.paligot.jsonforms.kotlin.models.schema.Schema
import com.paligot.jsonforms.kotlin.models.uischema.Control

/**
 * Get [Property] in [Schema] from scope property declared in the [Control].
 * It will search inside the schema, according to the path of the scope, the last key in the scope.
 * If it finds a property which isn't an object, it will return it. Otherwise, an error is thrown.
 *
 * ```kotlin
 * val schema = Schema(
 *     properties = persistentMapOf("key" to StringProperty())
 * )
 * val control = Control(scope = "#/properties/key")
 * val property = schema.getPropertyByControl<StringProperty>(control)
 * ```
 *
 * @param control Field contained in the UiSchema.
 * @return property from the schema.
 */
internal fun <T : Property> ObjectProperty.getPropertyByControl(control: Control): T {
    val propertyPath = control.propertyPath()
    var objectProperty: ObjectProperty = this
    for (key in propertyPath) {
        val property = objectProperty.properties[key]
            ?: error("Property $key not found in schema")
        if (property is ObjectProperty) {
            objectProperty = property
        } else if (propertyPath.last() == key) {
            return property as T
        } else {
            error("$key is not the latest key in the path and isn't an object")
        }
    }
    error("Can't find property with control $control")
}
