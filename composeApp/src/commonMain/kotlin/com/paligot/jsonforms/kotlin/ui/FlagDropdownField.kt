package com.paligot.jsonforms.kotlin.ui

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
import coil3.compose.AsyncImage
import com.paligot.jsonforms.kotlin.internal.ext.value
import com.paligot.jsonforms.kotlin.models.schema.Property
import jsonforms_kotlin.composeapp.generated.resources.Res
import kotlinx.collections.immutable.ImmutableList

@Composable
fun FlagDropdownField(
    value: String?,
    values: ImmutableList<Property>,
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    onFlagClick: () -> Unit,
    onItemClick: (String) -> Unit,
    onDismissRequest: () -> Unit,
) {
    Box(modifier = modifier) {
        FlagField(
            flagId = value,
            expanded = expanded,
            modifier = Modifier.clickable(onClick = onFlagClick),
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest,
            content = {
                values.forEach {
                    ListItem(
                        headlineContent = { Text(it.title ?: "") },
                        leadingContent = {
                            AsyncImage(
                                model = Res.getUri("drawable/${it.const?.value() as String}.svg"),
                                contentDescription = null,
                                modifier = Modifier.size(32.dp),
                            )
                        },
                        modifier =
                            Modifier.clickable {
                                onItemClick(it.const?.value() ?: "")
                                onDismissRequest()
                            },
                    )
                }
            },
        )
    }
}

@Composable
internal fun FlagField(
    flagId: String?,
    expanded: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Icon(
            imageVector =
                if (expanded) {
                    Icons.Outlined.UnfoldLess
                } else {
                    Icons.Outlined.UnfoldMore
                },
            contentDescription = null,
            modifier = Modifier.size(32.dp),
        )
        if (flagId != null) {
            AsyncImage(
                model = Res.getUri("drawable/$flagId.svg"),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
            )
        }
    }
}
