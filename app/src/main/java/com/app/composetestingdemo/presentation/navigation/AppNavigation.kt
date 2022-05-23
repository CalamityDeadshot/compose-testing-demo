package com.app.composetestingdemo.presentation.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.composetestingdemo.presentation.screens.registration.RegistrationScreen
import com.app.composetestingdemo.presentation.screens.users.UsersScreen

@ExperimentalMaterialApi
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreen.Register.route) {
        composable(
            route = AppScreen.Register.route
        ) {
            RegistrationScreen(
                navHostController = navController
            )
        }

        composable(
            route = AppScreen.UsersList.route
        ) {
            UsersScreen()
        }
    }
}