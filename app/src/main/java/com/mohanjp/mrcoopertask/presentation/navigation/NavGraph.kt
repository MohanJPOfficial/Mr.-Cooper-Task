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
    navHostController: NavHostController,
    isUserAuthenticated: Boolean
) {

    NavHost(
        navController = navHostController,
        startDestination = if(isUserAuthenticated)
            Screen.HOME.route
        else
            Screen.LOGIN.route
    ) {
        composable(
            Screen.LOGIN.route
        ) {
            LoginScreen {
                navHostController.navigate(Screen.HOME.route) {
                    popUpTo(Screen.LOGIN.route) {
                        inclusive = true
                    }
                }
            }
        }

        composable(
            Screen.HOME.route
        ) {
            HomeScreen {
                navHostController.navigate(Screen.DETAIL.route)
            }
        }

        composable(
            Screen.DETAIL.route
        ) {
            DetailScreen {
                navHostController.navigate(Screen.LOGIN.route) {
                    popUpTo(navHostController.graph.id) {
                        inclusive = true
                    }
                }
            }
        }
    }
}