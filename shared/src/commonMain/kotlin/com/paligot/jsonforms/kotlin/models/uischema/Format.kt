package com.paligot.jsonforms.kotlin.models.uischema

/**
 * The different formats.
 */
enum class Format {
    /**
     * Format that display a radio to the associated element.
     */
    Radio,

    /**
     * Format that display a toggle to the associated element.
     */
    Toggle,

    /**
     * Format that display a text input in password mode to the associated element.
     */
    Password,

    /**
     * Format that display a text input in email mode to the associated element.
     */
    Email,

    /**
     * Format that display a text input in phone mode to the associated element.
     */
    Phone,

    /**
     * Format that display a date picker to the associated element.
     */
    Date,
}
