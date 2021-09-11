package com.example.pollcompose.presentation

sealed class Screen(val route : String){
    object SplashScreen : Screen("splash_screen")
    object LoginScreen : Screen("login_screen")
    object SignUpScreen : Screen("signup_screen")
    class MainScreen(userId : String) : Screen("main_screen/$userId"){
        companion object{
            const val NAVIGATION_ROUTE = "main_screen/{userId}"
            const val USER_ARGUMENT = "userId"
        }
    }
    object CreatePollScreen : Screen("create_poll")
}
