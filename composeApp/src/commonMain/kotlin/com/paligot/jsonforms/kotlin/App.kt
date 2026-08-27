package com.paligot.jsonforms.kotlin

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.svg.SvgDecoder

@Composable
fun App() {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .components {
                add(SvgDecoder.Factory())
            }
            .build()
    }
    
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = FormList::class,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        builder = {
            formDescriptionNavGraph(navController)
        },
    )
}
