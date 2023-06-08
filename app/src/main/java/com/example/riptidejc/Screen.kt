package com.example.riptidejc

sealed class Screen(val route: String) {
    object SignupScreen: Screen("signup_screen")
    object SigninScreen: Screen("signin_screen")
    object MyQuestionsScreen: Screen("myquestions_screen")
    object NewQuestionScreen: Screen("newquestion_screen")
}
