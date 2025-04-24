package com.paligot.jsonforms.ui

import androidx.compose.runtime.Stable
import com.paligot.jsonforms.kotlin.models.uischema.GroupLayout
import com.paligot.jsonforms.kotlin.models.uischema.HorizontalLayout
import com.paligot.jsonforms.kotlin.models.uischema.UiSchema
import com.paligot.jsonforms.kotlin.models.uischema.VerticalLayout

@Stable
interface RendererLayoutScope {
    fun isVerticalLayout(): Boolean

    fun isHorizontalLayout(): Boolean

    fun isGroupLayout(): Boolean

    fun title(): String?

    fun description(): String?

    fun elements(): List<UiSchema>

    fun verticalSpacing(): String?

    fun horizontalSpacing(): String?
}

internal class RendererLayoutScopeInstance(
    private val uiSchema: UiSchema,
) : RendererLayoutScope {
    override fun isVerticalLayout(): Boolean = uiSchema is VerticalLayout

    override fun isHorizontalLayout(): Boolean = uiSchema is HorizontalLayout

    override fun isGroupLayout(): Boolean = uiSchema is GroupLayout

    override fun title(): String? = if (uiSchema is GroupLayout) uiSchema.label else null

    override fun description(): String? = if (uiSchema is GroupLayout) uiSchema.description else null

    override fun elements(): List<UiSchema> = uiSchema.elements?.toList() ?: emptyList()

    override fun verticalSpacing(): String? =
        when (uiSchema) {
            is VerticalLayout -> uiSchema.options?.verticalSpacing
            is HorizontalLayout -> uiSchema.options?.verticalSpacing
            is GroupLayout -> uiSchema.options?.verticalSpacing
            else -> null
        }

    override fun horizontalSpacing(): String? =
        when (uiSchema) {
            is VerticalLayout -> uiSchema.options?.horizontalSpacing
            is HorizontalLayout -> uiSchema.options?.horizontalSpacing
            is GroupLayout -> uiSchema.options?.horizontalSpacing
            else -> null
        }
}
