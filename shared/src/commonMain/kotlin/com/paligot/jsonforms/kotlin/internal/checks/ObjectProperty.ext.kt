package com.paligot.jsonforms.kotlin.internal.checks

import com.paligot.jsonforms.kotlin.internal.FieldError
import com.paligot.jsonforms.kotlin.internal.ext.propertyPath
import com.paligot.jsonforms.kotlin.models.schema.BooleanProperty
import com.paligot.jsonforms.kotlin.models.schema.NumberProperty
import com.paligot.jsonforms.kotlin.models.schema.ObjectProperty
import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import com.paligot.jsonforms.kotlin.models.uischema.Control

/**
 * Validates the given data against the schema defined in this `ObjectProperty`.
 *
 * This function checks for the following:
 * - Required fields: Ensures that all fields marked as required are present and valid.
 * - Property validation: Validates each property based on its type (e.g., `StringProperty`, `BooleanProperty`, etc.).
 * - Combinator validation: Evaluates `anyOf` conditions to ensure the data satisfies the schema's constraints.
 *
 * @param data A `Map` containing the data to validate against the schema.
 * @param keys A list of keys representing the properties to validate.
 * @return A list of [FieldError] objects representing validation errors, or an empty list if the data is valid.
 */
internal fun ObjectProperty.validate(
    data: Map<String, Any?>,
    keys: List<String> = properties.keys.toList()
): List<FieldError> {
    val requiredErrors = this.required
        .mapNotNull {
            val value = data[it]
            if (value == null) {
                FieldError.RequiredFieldError(it)
            } else if (value is String && value == "") {
                FieldError.RequiredFieldError(it)
            } else if (value is Boolean && value.not()) {
                FieldError.RequiredFieldError(it)
            } else {
                null
            }
        }
    val errors = this.properties
        .map { entry ->
            val property = entry.value
            if (property is ObjectProperty) {
                return@map property.validate(data, keys)
            }
            val value = data[entry.key]
            if (value == null || value == "" || keys.contains(entry.key).not()) {
                return@map emptyList()
            }
            when (property) {
                is StringProperty -> property.validate(entry.key, value as String)
                is BooleanProperty -> property.validate(entry.key, value as Boolean)
                is NumberProperty -> property.validate(entry.key, value as String)
                else -> emptyList()
            }
        }
        .flatten()

    val combinators = if (anyOf != null) {
        validateAnyOf(data)
    } else {
        emptyList()
    }

    return (requiredErrors + errors + combinators).distinct()
}

/**
 * Validates the `anyOf` conditions defined in this `ObjectProperty` against the provided data.
 *
 * The `anyOf` condition is satisfied if at least one of the schemas in the `anyOf` list is valid
 * for the given data. This function iterates through the `anyOf` schemas and validates the data
 * against each schema. If none of the schemas are valid, it returns a list of errors
 *
 * @param data A `Map` containing the data to validate against the `anyOf` conditions.
 * @return A list of [FieldError] objects representing validation errors, or an empty list if
 *         at least one `anyOf` condition is satisfied.
 */
private fun ObjectProperty.validateAnyOf(data: Map<String, Any?>): List<FieldError> {
    if (this.anyOf == null) {
        return emptyList()
    }
    val minErrors = mutableListOf<FieldError>()
    for (objectProperty in this.anyOf) {
        val errors = objectProperty.validate(data)
        if (errors.isEmpty()) {
            return errors
        }
        if (minErrors.isEmpty() || errors.size < minErrors.size) {
            minErrors.clear()
            minErrors.addAll(errors)
        }
    }
    return minErrors
}

/**
 * Check if the property is tagged as required in the [com.paligot.jsonforms.kotlin.models.schema.Schema].
 *
 * In addition of the required list and anyOf statements will be evaluated to apply field requirement
 *
 * ```kotlin
 * val schema = Schema(
 *     properties = persistentMapOf(
 *         "fieldA" to StringProperty(
 *             oneOf = persistentListOf(
 *                 StringProperty(const = JsonPrimitive("value1"), title = "Value 1"),
 *                 StringProperty(const = JsonPrimitive("value2"), title = "Value 2")
 *             )
 *         ),
 *         "fieldB" to StringProperty(),
 *         "fieldC" to StringProperty(),
 *         "fieldD" to StringProperty(),
 *         "fieldE" to StringProperty()
 *     ),
 *     anyOf = persistentListOf(
 *         ConditionProperty(required = persistentListOf("fieldD")),
 *         ConditionProperty(required = persistentListOf("fieldE"))
 *     ),
 *     required = persistentListOf("fieldA)
 * )
 * val control = Control(scope = "#/properties/key")
 * val isRequired = schema.propertyIsRequired(control)
 * ```
 *
 * @param control Field contained in the UiSchema.
 * @param data the [Map] of data to evaluate oneOf/anyOf statements to apply field requirements
 * @return is required property
 */
internal fun ObjectProperty.propertyIsRequired(control: Control, data: Map<String, Any?>): Boolean {
    var objectProperty: ObjectProperty = this
    val path = control.propertyPath()
    path.forEach {
        if (objectProperty.required.contains(it) ||
            objectProperty.anyOfRequired(data = data).contains(it)
        ) {
            return true
        }
        val property = objectProperty.properties[it] ?: error("$it not exist")
        if (property is ObjectProperty) {
            objectProperty = property
        } else {
            return false
        }
    }
    return false
}

/**
 * Evaluate anyOf statements with the given data in order to get the fields requirements
 * The anyOf statement is a OR : data must be valid against any of the statements
 *
 * @param data A [Map] containing the data to evaluate the allOf conditions.
 * @return a [List] of [String] with the required fields
 */
private fun ObjectProperty.anyOfRequired(data: Map<String, Any?>): List<String> {
    val matchingConditions = this.anyOf
        ?.filter { property -> property.validate(data).isEmpty() }
        ?: emptyList()
    if (matchingConditions.isNotEmpty()) {
        return matchingConditions.flatMap { it.required }.distinct()
    }
    return this.anyOf?.flatMap { it.required }?.distinct() ?: emptyList()
}
