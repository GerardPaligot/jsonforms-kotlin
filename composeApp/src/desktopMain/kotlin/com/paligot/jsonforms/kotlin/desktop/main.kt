
@file:Suppress("ktlint:standard:filename")

package com.paligot.jsonforms.kotlin.desktop

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.paligot.jsonforms.kotlin.App
import com.paligot.jsonforms.kotlin.ui.MyApplicationTheme

fun main() =
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Jsonform Sample",
        ) {
            MyApplicationTheme {
                App()
            }
        }
    }
