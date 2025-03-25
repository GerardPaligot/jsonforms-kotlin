package com.paligot.jsonforms.kotlin.internal.queries

import com.paligot.jsonforms.kotlin.internal.ControlLayout
import com.paligot.jsonforms.kotlin.internal.ext.propertyKey
import com.paligot.jsonforms.kotlin.models.uischema.Control
import com.paligot.jsonforms.kotlin.models.uischema.GroupLayout
import com.paligot.jsonforms.kotlin.models.uischema.HorizontalLayout
import com.paligot.jsonforms.kotlin.models.uischema.UiSchema
import com.paligot.jsonforms.kotlin.models.uischema.VerticalLayout
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

/**
 * Find [Control] from keys declared in [keys] parameters and attach the parent [UiSchema] layout or null
 * if the [Control] is the root element.
 *
 * ```kotlin
 * val uiSchema = vertical {
 *     control(scope = "#/properties/key1") {}
 *     control(scope = "#/properties/key2") {}
 * }
 * val keys = listOf("key2")
 * val uiControls = uiSchema.findUiControls(keys)
 * ```
 *
 * @param keys List of keys declared at the end of a scope
 * @return Map of [ControlLayout] with the associated key
 */
suspend fun UiSchema.findUiControls(keys: List<String>): Map<String, ControlLayout?> =
    coroutineScope {
        return@coroutineScope keys
            .map { key -> async { findUiControl(parent = this@findUiControls, key = key) } }
            .awaitAll()
            .filterNotNull()
            .toMap()
    }

private suspend fun UiSchema.findUiControl(
    parent: UiSchema,
    key: String
): Pair<String, ControlLayout>? =
    coroutineScope {
        return@coroutineScope when (this@findUiControl) {
            is GroupLayout -> findUiControlFromLayout(key)
            is HorizontalLayout -> findUiControlFromLayout(key)
            is VerticalLayout -> findUiControlFromLayout(key)
            is Control -> {
                if (propertyKey() == key) {
                    return@coroutineScope key to ControlLayout(
                        layout = parent,
                        control = this@findUiControl
                    )
                }
                return@coroutineScope null
            }

            else -> TODO("Implement UiSchema subclass")
        }
    }

private suspend fun UiSchema.findUiControlFromLayout(key: String): Pair<String, ControlLayout>? =
    coroutineScope {
        return@coroutineScope elements
            ?.map { async { it.findUiControl(this@findUiControlFromLayout, key) } }
            ?.awaitAll()
            ?.find { it != null }
    }
