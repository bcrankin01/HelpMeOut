package com.example.riptidejc

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

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
            NewQuestionScreen(navController)
        }
        composable(
            route = Screen.TeacherFeedScreen.route
        ) {
            TeacherFeedScreen()
        }
        composable(
            route = "${Screen.ViewQuestionScreen.route}/{questionId}",
            arguments = listOf(navArgument("questionId") { type = NavType.StringType })
        ) {
            val questionId = it.arguments?.getString("questionId")
            ViewQuestionScreen(navController, questionId ?: "")
        }

    }
}
