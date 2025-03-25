package com.paligot.jsonforms.kotlin.internal.ext

import com.paligot.jsonforms.kotlin.models.schema.StringProperty
import com.paligot.jsonforms.kotlin.models.uischema.Control
import com.paligot.jsonforms.kotlin.models.uischema.Format

/**
 * Check if [StringProperty] is a radio based on the format and enum or oneOf fields.
 *
 * ```kotlin
 * val property = StringProperty(enum = arrayListOf(""))
 * val control = Control(
 *     scope = "#/properties/key",
 *     options = Options(format = Format.Radio)
 * )
 * val isRadio = property.isRadio(control)
 * ```
 *
 * @param control Field contained in the [com.decathlon.jsonforms.models.uischeme.UiSchema]
 * @return true if the [StringProperty] is a radio
 */
fun StringProperty.isRadio(control: Control) =
    (control.options?.format == Format.Radio && enum != null && enum?.size != 0) ||
        (control.options?.format == Format.Radio && oneOf != null && oneOf?.size != 0)

/**
 * Check if [StringProperty] is a password based on the format.
 *
 * ```kotlin
 * val property = StringProperty()
 * val control = Control(
 *     scope = "#/properties/key",
 *     options = Options(format = Format.Password)
 * )
 * val isPassword = property.isPassword(control)
 * ```
 *
 * @param control Field contained in the [com.decathlon.jsonforms.models.uischeme.UiSchema]
 * @return true if the [StringProperty] is a password
 */
fun StringProperty.isPassword(control: Control) =
    control.options?.format == Format.Password

/**
 * Check if [StringProperty] is an email based on the format.
 *
 * ```kotlin
 * val property = StringProperty()
 * val control = Control(
 *     scope = "#/properties/key",
 *     options = Options(format = Format.Email)
 * )
 * val isPassword = property.isEmail(control)
 * ```
 *
 * @param control Field contained in the [com.decathlon.jsonforms.models.uischeme.UiSchema]
 * @return true if the [StringProperty] is an email
 */
fun StringProperty.isEmail(control: Control) =
    control.options?.format == Format.Email

/**
 * Check if [StringProperty] is a phone based on the format.
 *
 * ```kotlin
 * val property = StringProperty()
 * val control = Control(
 *     scope = "#/properties/key",
 *     options = Options(format = Format.Phone)
 * )
 * val isPassword = property.isPhone(control)
 * ```
 *
 * @param control Field contained in the [com.decathlon.jsonforms.models.uischeme.UiSchema]
 * @return true if the [StringProperty] is a phone
 */
fun StringProperty.isPhone(control: Control) =
    control.options?.format == Format.Phone

/**
 * Check if [StringProperty] is a dropdown based on the oneOf field.
 *
 * ```kotlin
 * val property = StringProperty(
 *     oneOf = arrayListOf(PropertyValue(const = "", title = ""))
 * )
 * val isDropdown = property.isDropdown()
 * ```
 *
 * @return true if the [StringProperty] is a dropdown
 */
fun StringProperty.isDropdown() = oneOf != null && oneOf?.size != 0
