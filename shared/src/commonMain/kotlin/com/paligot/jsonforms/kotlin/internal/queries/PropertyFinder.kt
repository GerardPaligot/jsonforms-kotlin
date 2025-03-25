package com.paligot.jsonforms.kotlin.internal.queries

import com.paligot.jsonforms.kotlin.internal.ext.propertyKey
import com.paligot.jsonforms.kotlin.internal.ext.propertyPath
import com.paligot.jsonforms.kotlin.internal.ext.resolve
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
 *     properties = mutableMapOf("key" to StringProperty())
 * )
 * val control = Control(scope = "#/properties/key")
 * val property = schema.getPropertyByControl<StringProperty>(control)
 * ```
 *
 * @param control Field contained in the UiSchema.
 * @return property from the schema.
 */
fun <T : Property> Schema.getPropertyByControl(control: Control): T {
    val path = control.propertyPath()
    var objectProperty: ObjectProperty = this
    path.forEachIndexed { index, key ->
        val property = objectProperty.properties[key] ?: error("$key not exist")
        if (property is ObjectProperty && index != path.size - 1) {
            objectProperty = property
        } else if (property is ObjectProperty && index == path.size - 1) {
            error("$key is an object. The last key in the scope path can only be a string, number, boolean or array.")
        } else if (property !is ObjectProperty && index != path.size - 1) {
            error("$key isn't an object. Key before the last one in the scope path can only be object property.")
        } else if (property !is ObjectProperty && index == path.size - 1 && key == control.propertyKey()) {
            return property as T
        } else {
            error("Internal error")
        }
    }
    error("Internal error")
}

/**
 * Get required keys specified in root [Schema] object and [ObjectProperty]
 * declared at the level 1. If you specify an object property at a level bigger
 * than 1, it will be ignored.
 *
 *  OneOf/anyOf statements required list will be taken into account as well
 *
 * ```kotlin
 * val schema = Schema(
 *             properties = mutableMapOf(
 *                 "fieldA" to StringProperty(
 *                     oneOf = listOf(
 *                         PropertyValue(
 *                             const = "value1",
 *                             title = "Value 1"
 *                         ),
 *                         PropertyValue(
 *                             const = "value2",
 *                             title = "Value 2"
 *                         )
 *                     )
 *                 ),
 *                 "fieldB" to StringProperty(),
 *                 "fieldC" to StringProperty(),
 *                 "fieldD" to StringProperty(),
 *                 "fieldE" to StringProperty()
 *             ),
 *             oneOf = listOf(
 *                 ConditionProperty(
 *                     properties = mapOf("fieldA" to ConditionSchema(enum = listOf("value1"))),
 *                     required = listOf("fieldA", "fieldB")
 *                 ),
 *                 ConditionProperty(
 *                     properties = mapOf("fieldA" to ConditionSchema(enum = listOf("value2"))),
 *                     required = listOf("fieldA", "fieldC")
 *                 )
 *             ),
 *             anyOf = listOf(
 *                 ConditionProperty(
 *                     required = listOf("fieldD")
 *                 ), ConditionProperty(
 *                     required = listOf("fieldE")
 *                 )
 *             )
 *             required = listOf("fieldA)
 *         )
 * val keys = schema.requiredKeys()
 * ```
 * @return required keys list
 */
fun Schema.requiredKeys(): List<String> {
    val list = required.toMutableList()
    for (property in properties.values) {
        if (property !is ObjectProperty) {
            continue
        }
        list.addAll(property.required)
    }
    val oneOfRequired = this.oneOf?.flatMap { oneOf -> oneOf.required } ?: emptyList()
    val anyOfRequired = this.anyOf?.flatMap { oneOf -> oneOf.required } ?: emptyList()
    return list.union(oneOfRequired).union(anyOfRequired).distinct()
}

/**
 * Check if the property is tagged as required in the [Schema].
 *
 * In addition of the required list, oneOf and anyOf statements will be evaluated to apply field requirement
 *
 * ```kotlin
 * val schema = Schema(
 *             properties = mutableMapOf(
 *                 "fieldA" to StringProperty(
 *                     oneOf = listOf(
 *                         PropertyValue(
 *                             const = "value1",
 *                             title = "Value 1"
 *                         ),
 *                         PropertyValue(
 *                             const = "value2",
 *                             title = "Value 2"
 *                         )
 *                     )
 *                 ),
 *                 "fieldB" to StringProperty(),
 *                 "fieldC" to StringProperty(),
 *                 "fieldD" to StringProperty(),
 *                 "fieldE" to StringProperty()
 *             ),
 *             oneOf = listOf(
 *                 ConditionProperty(
 *                     properties = mapOf("fieldA" to ConditionSchema(enum = listOf("value1"))),
 *                     required = listOf("fieldA", "fieldB")
 *                 ),
 *                 ConditionProperty(
 *                     properties = mapOf("fieldA" to ConditionSchema(enum = listOf("value2"))),
 *                     required = listOf("fieldA", "fieldC")
 *                 )
 *             ),
 *             anyOf = listOf(
 *                 ConditionProperty(
 *                     required = listOf("fieldD")
 *                 ), ConditionProperty(
 *                     required = listOf("fieldE")
 *                 )
 *             )
 *             required = listOf("fieldA)
 *         )
 *
 * val control = Control(scope = "#/properties/key")
 * val isRequired = schema.propertyIsRequired(control)
 * ```
 *
 * @param control Field contained in the UiSchema.
 * @param data the [Map] of data to evaluate oneOf/anyOf statements to apply field requirements
 * @return is required property
 */
fun Schema.propertyIsRequired(control: Control, data: Map<String, Any?>): Boolean {
    var objectProperty: ObjectProperty = this
    val path = control.propertyPath()
    path.forEach {
        if (objectProperty.required.contains(it) ||
            this.oneOfRequired(data = data).contains(it) ||
            this.anyOfRequired(data = data).contains(it)
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
 * Evaluate oneOf statements with the given data in order to get the fields requirements
 * The oneOf statement is a XOR : data must be valid against exactly one of the statements
 * If no oneOf condition statement matches, all the fields involved in the oneOf statements will be required
 *
 * @return a [List] of [String] with the required fields
 */
fun Schema.oneOfRequired(data: Map<String, Any?>): List<String> {
    var oneOfRequired = this.oneOf?.find { oneOf ->
        oneOf.properties.any { property ->
            property.value.resolve(
                key = property.key,
                data = data
            )
        }
    }?.required ?: emptyList()

    if (oneOfRequired.isEmpty() && this.oneOf != null) {
        oneOfRequired = this.oneOf!!.flatMap { oneOf -> oneOf.required }.distinct()
    }
    return oneOfRequired
}

/**
 * Evaluate anyOf statements with the given data in order to get the fields requirements
 * The anyOf statement is a OR : data must be valid against any of the statements
 * If no anyOF condition statement matches, all the fields involved in the oneOf statements will be required
 *
 * @return a [List] of [String] with the required fields
 */
fun Schema.anyOfRequired(data: Map<String, Any?>): List<String> {
    val dataMatchesAtLeastOneAnyOfStatement = this.anyOf?.any { anyOf ->
        anyOf.properties.any { property ->
            property.value.resolve(
                property.key,
                data
            )
        } || (
            anyOf.properties.isEmpty() && anyOf.required.any {
                data.keys.contains(it) &&
                    (data[it] is String && data[it] != "" || data[it] is Boolean && (data[it] as Boolean).not())
            }
            )
    } ?: false

    if (dataMatchesAtLeastOneAnyOfStatement || this.anyOf == null) {
        return emptyList()
    }

    return this.anyOf!!.flatMap { anyOf -> anyOf.required }.distinct()
}
