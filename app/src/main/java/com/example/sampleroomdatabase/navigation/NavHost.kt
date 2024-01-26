package com.example.sampleroomdatabase.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sampleroomdatabase.data.settings.ThemeSettings
import com.example.sampleroomdatabase.data.settings.UserSettings
import com.example.sampleroomdatabase.presentation.ContactViewModel
import com.example.sampleroomdatabase.presentation.ui.fillMaxWidthInHorizontal
import com.example.sampleroomdatabase.presentation.ui.screens.ContactDetailScreen
import com.example.sampleroomdatabase.presentation.ui.screens.CreateContactScreen
import com.example.sampleroomdatabase.presentation.ui.screens.MainScreen
import com.example.sampleroomdatabase.presentation.ui.screens.SettingsScreen

sealed class Screens(val destination: String) {
    data object MainScreen : Screens("MainScreen")
    data object CreateContactScreen : Screens("CreateContactScreen")
    data object SettingsScreen : Screens("SettingsScreen")
    data object ContactDetailScreen : Screens("ContactDetailScreen")
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun AppNavHost(
    theme: ThemeSettings,
    dynamicColor: Boolean,
    amoledColor: Boolean,
    userSettings: UserSettings,
    contactViewModel: ContactViewModel = viewModel(factory = ContactViewModel.factory)
) {
    val navController = rememberNavController()
    val contacts by contactViewModel.allContacts.collectAsState(initial = emptyList())

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
        composable(Screens.MainScreen.destination) {
            MainScreen(
                navController = navController,
                contacts = contacts,
                onContactDetail = {
                    navController.navigate(Screens.ContactDetailScreen.destination + "/$it")
                },
                modifier = Modifier.fillMaxWidthInHorizontal()
            )
        }
        composable(Screens.CreateContactScreen.destination) {
            CreateContactScreen(navController, contacts)
        }
        composable(Screens.SettingsScreen.destination) {
            SettingsScreen(theme, dynamicColor, amoledColor, userSettings, navController)
        }
        composable(
            route = Screens.ContactDetailScreen.destination + "/{$CONTACT_ID}",
            arguments = listOf(navArgument(CONTACT_ID) { type = NavType.LongType })
        ) {
            val selectedContact = contacts.firstOrNull { contact ->
                contact.id == it.arguments?.getLong(CONTACT_ID)
            }
            ContactDetailScreen(navController, selectedContact)
        }
    }
}

private const val CONTACT_ID = "contact_id"
