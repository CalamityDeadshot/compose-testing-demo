package com.app.composetestingdemo.presentation.navigation

sealed class AppScreen(
    val route: String
) {
    object Register: AppScreen("register")
    object UsersList: AppScreen("list")
}
