package com.example.pollcompose

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.lifecycle.viewmodel.*
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.pollcompose.presentation.Screen
import com.example.pollcompose.presentation.ui.login.LoginScreen
import com.example.pollcompose.presentation.ui.login.LoginViewModel
import com.example.pollcompose.presentation.ui.main.MainScreen
import com.example.pollcompose.presentation.ui.main.MainViewModel
import com.example.pollcompose.presentation.ui.signup.SignUpScreen
import com.example.pollcompose.presentation.ui.signup.SignUpViewModel
import com.example.pollcompose.presentation.ui.splash.SplashScreen
import com.example.pollcompose.presentation.ui.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalUnitApi
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.SplashScreen.route){
                composable(Screen.SplashScreen.route){
                    SplashScreen(navigatePage = navController::navigate)
                }
                composable(Screen.LoginScreen.route){
                    LoginScreen(navigatePage = navController::navigate)
                }
                composable(Screen.SignUpScreen.route){
                    SignUpScreen(navigatePage = navController::navigate) }
                composable(Screen.MainScreen.NAVIGATION_ROUTE,
                    arguments = listOf(navArgument(Screen.MainScreen.USER_ARGUMENT) {
                        type = NavType.StringType
                    })
                ){ backStackEntry->
                    MainScreen(
                        userId = backStackEntry.arguments?.getString(Screen.MainScreen.USER_ARGUMENT)!!,
                        navigatePage = navController::navigate
                    )
                }
            }
        }
    }
}