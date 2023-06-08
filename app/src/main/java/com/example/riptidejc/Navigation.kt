package com.example.riptidejc

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SigninScreen.route
    ) {
        composable(
            route = Screen.SignupScreen.route
        ) {
            SignupScreen(navController)
        }
        composable(
            route = Screen.SigninScreen.route
        ) {
            SigninScreen(navController)
        }
        composable(
            route = Screen.MyQuestionsScreen.route
        ) {
            MyQuestionsScreen(navController)
        }
        composable(
            route = Screen.NewQuestionScreen.route
        ) {
            NewQuestionScreen()
        }
        composable(
            route = Screen.TeacherFeedScreen.route
        ) {
            TeacherFeedScreen()
        }
    }
}
