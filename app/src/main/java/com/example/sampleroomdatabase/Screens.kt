package com.example.sampleroomdatabase

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sampleroomdatabase.screens.CreateContactScreen
import com.example.sampleroomdatabase.screens.MainScreen
import com.example.sampleroomdatabase.screens.SettingsScreen
import com.example.sampleroomdatabase.ui.theme.ThemeSettings

sealed class Screens(val destination: String) {
    data object MainScreen : Screens("MainScreen")
    data object CreateContactScreen : Screens("CreateContactScreen")
    data object SettingsScreen : Screens("SettingsScreen")
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun AppNavHost(
    contactViewModel: ContactViewModel = viewModel(factory = ContactViewModel.factory),
    theme: ThemeSettings,
    dynamicColor: Boolean,
    amoledColor: Boolean,
    userSettings: UserSettings
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.MainScreen.destination,
        enterTransition = {
            fadeIn(
                animationSpec = tween(
                    durationMillis = 250,
                    easing = LinearEasing
                )
            ) + slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(400)
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(
                    durationMillis = 250,
                    easing = LinearEasing
                )
            ) + slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(400)
            )
        },
        popEnterTransition = {
            fadeIn(
                animationSpec = tween(
                    durationMillis = 250,
                    easing = LinearEasing
                )
            ) + slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(400)
            )
        },
        popExitTransition = {
            fadeOut(
                animationSpec = tween(
                    durationMillis = 250,
                    easing = LinearEasing
                )
            ) + slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(400)
            )
        }
    ) {
        composable(Screens.MainScreen.destination) { MainScreen(navController) }
        composable(Screens.CreateContactScreen.destination) { CreateContactScreen(navController) }
        composable(Screens.SettingsScreen.destination) {
            SettingsScreen(
                navController = navController,
                theme = theme,
                dynamicColor = dynamicColor,
                amoledColor = amoledColor,
                userSettings = userSettings
            )
        }
    }
}