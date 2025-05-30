package com.paligot.jsonforms.cupertino.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.paligot.jsonforms.kotlin.internal.ext.value
import com.paligot.jsonforms.kotlin.models.schema.Property
import com.slapps.cupertino.CupertinoSegmentedControl
import com.slapps.cupertino.CupertinoSegmentedControlTab
import com.slapps.cupertino.CupertinoText
import com.slapps.cupertino.ExperimentalCupertinoApi
import com.slapps.cupertino.theme.CupertinoTheme
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalCupertinoApi::class)
@Composable
internal fun SegmentedControl(
    value: String?,
    values: ImmutableList<Property>,
    modifier: Modifier = Modifier,
    label: String? = null,
    description: String? = null,
    error: String? = null,
    onValueChange: (String) -> Unit,
) {
    Column(modifier = modifier) {
        label?.let {
            CupertinoText(
                text = it,
                style = CupertinoTheme.typography.subhead,
            )
        }
        if (error != null) {
            CupertinoText(text = error, color = Color.Red)
        } else if (description != null) {
            CupertinoText(text = description)
        }
        val selectedTabIndex = values.indexOfFirst { it.const?.value<String>() == value }
        CupertinoSegmentedControl(
            modifier = Modifier.fillMaxWidth(),
            selectedTabIndex = if (selectedTabIndex == -1) 0 else selectedTabIndex,
            tabs = {
                values.forEach {
                    CupertinoSegmentedControlTab(
                        onClick = { onValueChange.invoke(it.const?.value() ?: "") },
                        isSelected = value == it.const?.value<String>(),
                        content = {
                            CupertinoText(text = it.title ?: "")
                        },
                    )
                }
            },
        )
    }
}
