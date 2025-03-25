package com.paligot.jsonforms.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.paligot.jsonforms.kotlin.SchemaProvider
import com.paligot.jsonforms.kotlin.internal.ext.isDropdown
import com.paligot.jsonforms.kotlin.internal.ext.isEmail
import com.paligot.jsonforms.kotlin.internal.ext.isEnabled
import com.paligot.jsonforms.kotlin.internal.ext.isPassword
import com.paligot.jsonforms.kotlin.internal.ext.isPhone
import com.paligot.jsonforms.kotlin.internal.ext.isRadio
import com.paligot.jsonforms.kotlin.internal.ext.label
import com.paligot.jsonforms.kotlin.models.schema.PropertyValue
import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import com.paligot.jsonforms.kotlin.models.uischema.Control
import com.paligot.jsonforms.kotlin.models.uischema.Orientation
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Stable
interface RendererStringScope {
    fun isRadio(): Boolean
    fun isDropdown(): Boolean
    fun label(): String?
    fun description(): String?
    fun values(): ImmutableList<PropertyValue>
    fun enabled(): Boolean
    fun orientation(): Orientation
    fun verticalArrangement(): Arrangement.Vertical
    fun horizontalArrangement(): Arrangement.Horizontal
    fun visualTransformation(): VisualTransformation
    fun keyboardOptions(
        capitalization: KeyboardCapitalization = KeyboardCapitalization.Unspecified,
        keyboardType: KeyboardType = KeyboardType.Unspecified,
        imeAction: ImeAction = ImeAction.Unspecified
    ): KeyboardOptions
}

internal class RendererStringScopeInstance(
    private val control: Control,
    private val schemaProvider: SchemaProvider,
    private val jsonFormState: JsonFormState,
    private val property: StringProperty = schemaProvider.getPropertyByControl(control)
) : RendererStringScope {
    override fun isRadio(): Boolean = property.isRadio(control)

    override fun isDropdown(): Boolean = property.isDropdown()

    override fun label(): String? = property
        .label(
            required = schemaProvider.propertyIsRequired(control, jsonFormState.getData()),
            control = control
        )

    override fun description(): String? = property.description

    override fun values(): ImmutableList<PropertyValue> {
        if (property.enum != null && property.enum!!.isNotEmpty()) {
            return property.enum!!.map { PropertyValue(it, it) }.toImmutableList()
        }
        return property.oneOf ?: persistentListOf()
    }

    override fun enabled(): Boolean = property.isEnabled(control, jsonFormState.getData())

    override fun orientation(): Orientation = control.options?.orientation
        ?: Orientation.VERTICALLY

    override fun verticalArrangement(): Arrangement.Vertical = control.options
        ?.verticalSpacing
        ?.let { Arrangement.spacedBy(it.toInt().dp) }
        ?: run { Arrangement.Top }

    override fun horizontalArrangement(): Arrangement.Horizontal = control.options
        ?.horizontalSpacing
        ?.let { Arrangement.spacedBy(it.toInt().dp) }
        ?: run { Arrangement.Start }

    override fun visualTransformation(): VisualTransformation =
        if (property.isPassword(control)) PasswordVisualTransformation() else VisualTransformation.None

    override fun keyboardOptions(
        capitalization: KeyboardCapitalization, keyboardType: KeyboardType, imeAction: ImeAction
    ): KeyboardOptions = KeyboardOptions(
        capitalization = if (control.options?.hasFirstLetterCapitalized == true) {
            KeyboardCapitalization.Sentences
        } else {
            capitalization
        },
        keyboardType = when {
            property.isEmail(control) -> KeyboardType.Email
            property.isPassword(control) -> KeyboardType.Password
            property.isPhone(control) -> KeyboardType.Phone
            else -> keyboardType
        },
        imeAction = imeAction
    )
}
