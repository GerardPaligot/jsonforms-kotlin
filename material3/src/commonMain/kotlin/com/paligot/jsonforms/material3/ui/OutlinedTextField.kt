package com.paligot.jsonforms.material3.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun OutlinedTextField(
    value: String?,
    modifier: Modifier = Modifier,
    label: String? = null,
    description: String? = null,
    enabled: Boolean = true,
    error: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val supportingText = error ?: description
    androidx.compose.material3.OutlinedTextField(
        value = value ?: "",
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        isError = error != null,
        label =
            if (label != null) {
                { Text(text = label) }
            } else {
                null
            },
        supportingText =
            if (supportingText != null) {
                { Text(text = supportingText) }
            } else {
                null
            },
        keyboardOptions = keyboardOptions,
        keyboardActions =
            KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                onDone = { focusManager.clearFocus(true) },
            ),
        maxLines = 1,
        singleLine = true,
        visualTransformation = visualTransformation,
    )
}
