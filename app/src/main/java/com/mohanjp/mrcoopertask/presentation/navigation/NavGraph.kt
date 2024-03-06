package com.mohanjp.mrcoopertask.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mohanjp.mrcoopertask.presentation.detail.DetailScreen
import com.mohanjp.mrcoopertask.presentation.home.HomeScreen
import com.mohanjp.mrcoopertask.presentation.login.LoginScreen

@Composable
fun NavGraph(
    navHostController: NavHostController
) {

    NavHost(
        navController = navHostController,
        startDestination = Screen.LOGIN.route
    ) {
        composable(
            Screen.LOGIN.route
        ) {
            LoginScreen()
        }

        composable(
            Screen.HOME.route
        ) {
            HomeScreen()
        }

        composable(
            Screen.DETAIL.route
        ) {
            DetailScreen()
        }
    }
}