package com.paligot.jsonforms.cupertino.layout

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slapps.cupertino.CupertinoText
import com.slapps.cupertino.ExperimentalCupertinoApi
import com.slapps.cupertino.section.CupertinoSection
import com.slapps.cupertino.section.SectionScope

@OptIn(ExperimentalCupertinoApi::class)
@Composable
internal fun CupertinoSection(
    title: String?,
    description: String?,
    modifier: Modifier = Modifier,
    content: @Composable SectionScope.() -> Unit,
) {
    CupertinoSection(
        title =
            if (title != null) {
                { CupertinoText(text = title) }
            } else {
                null
            },
        caption =
            if (description != null) {
                { CupertinoText(text = description) }
            } else {
                null
            },
        modifier = modifier,
        content = content,
    )
}
