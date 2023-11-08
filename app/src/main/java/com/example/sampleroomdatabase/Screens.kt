package com.example.sampleroomdatabase

sealed class Screens(val destination: String) {
    data object MainScreen : Screens("MainScreen")
    data object CreateContactScreen : Screens("CreateContactScreen")
}