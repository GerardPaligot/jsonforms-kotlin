package com.paligot.jsonforms.cupertino.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.VisualTransformation
import com.slapps.cupertino.CupertinoText
import com.slapps.cupertino.CupertinoTextField
import com.slapps.cupertino.theme.CupertinoTheme

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
    onValueChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val supportingText = error ?: description
    Column {
        CupertinoTextField(
            value = value ?: "",
            onValueChange = onValueChange,
            modifier = modifier.fillMaxWidth(),
            enabled = enabled,
            isError = error != null,
            placeholder = if (label != null) {
                { CupertinoText(text = label) }
            } else {
                null
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                onDone = { focusManager.clearFocus(true) }
            ),
            maxLines = 1,
            singleLine = true,
            visualTransformation = visualTransformation,
        )
        if (supportingText != null) {
            CupertinoText(
                text = supportingText,
                style = CupertinoTheme.typography.caption1
            )
        }
    }
}
