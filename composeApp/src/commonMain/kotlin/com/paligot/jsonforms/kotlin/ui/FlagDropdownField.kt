package com.paligot.jsonforms.kotlin.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.UnfoldLess
import androidx.compose.material.icons.outlined.UnfoldMore
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paligot.jsonforms.kotlin.models.schema.PropertyValue
import jsonforms_kotlin.composeapp.generated.resources.Res
import jsonforms_kotlin.composeapp.generated.resources.de
import jsonforms_kotlin.composeapp.generated.resources.es
import jsonforms_kotlin.composeapp.generated.resources.fr
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.painterResource

@Composable
fun FlagDropdownField(
    value: String?,
    values: ImmutableList<PropertyValue>,
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    onFlagClick: () -> Unit,
    onItemClick: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    Box(modifier = modifier) {
        FlagField(
            flagId = value,
            expanded = expanded,
            modifier = Modifier.clickable(onClick = onFlagClick)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest,
            content = {
                values.forEach {
                    ListItem(
                        headlineContent = { Text(it.title) },
                        leadingContent = {
                            Image(
                                painter = painterResource(getFlagResource(it.const)),
                                contentDescription = null,
                                modifier = Modifier.size(32.dp)
                            )
                        },
                        modifier = Modifier.clickable {
                            onItemClick(it.const)
                            onDismissRequest()
                        }
                    )
                }
            }
        )
    }
}

@Composable
internal fun FlagField(
    flagId: String?,
    expanded: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = if (expanded) {
                Icons.Outlined.UnfoldLess
            } else {
                Icons.Outlined.UnfoldMore
            },
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
        if (flagId != null) {
            Image(
                painter = painterResource(getFlagResource(flagId)),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

private fun getFlagResource(flagId: String) = when (flagId) {
    "de" -> Res.drawable.de
    "fr" -> Res.drawable.fr
    "es" -> Res.drawable.es
    else -> TODO("Flag not found")
}
