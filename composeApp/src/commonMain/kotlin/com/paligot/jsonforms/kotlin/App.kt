package com.paligot.jsonforms.kotlin

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = FormList::class,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        builder = {
            formDescriptionNavGraph(navController)
        }
    )
}
