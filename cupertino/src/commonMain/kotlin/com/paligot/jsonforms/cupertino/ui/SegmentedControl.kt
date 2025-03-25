package com.paligot.jsonforms.cupertino.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.paligot.jsonforms.kotlin.models.schema.PropertyValue
import com.slapps.cupertino.CupertinoSegmentedControl
import com.slapps.cupertino.CupertinoSegmentedControlTab
import com.slapps.cupertino.CupertinoText
import com.slapps.cupertino.ExperimentalCupertinoApi
import com.slapps.cupertino.theme.CupertinoTheme
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun SegmentedControl(
    value: String?,
    values: ImmutableList<PropertyValue>,
    modifier: Modifier = Modifier,
    label: String? = null,
    description: String? = null,
    error: String? = null,
    onValueChange: (String) -> Unit
) {
    Column(modifier = modifier) {
        label?.let {
            CupertinoText(
                text = it,
                style = CupertinoTheme.typography.subhead
            )
        }
        if (error != null) {
            CupertinoText(text = error, color = Color.Red)
        } else if (description != null) {
            CupertinoText(text = description)
        }
        val selectedTabIndex = values.indexOfFirst { it.const == value }
        CupertinoSegmentedControl(
            modifier = Modifier.fillMaxWidth(),
            selectedTabIndex = if (selectedTabIndex == -1) 0 else selectedTabIndex,
            tabs = {
                values.forEach {
                    CupertinoSegmentedControlTab(
                        onClick = { onValueChange.invoke(it.const) },
                        isSelected = value == it.const,
                        content = {
                            CupertinoText(text = it.title)
                        }
                    )
                }
            }
        )
    }
}
