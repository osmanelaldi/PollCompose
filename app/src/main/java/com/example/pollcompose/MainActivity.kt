package com.example.pollcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.lifecycle.viewmodel.*
import androidx.navigation.compose.rememberNavController
import com.example.pollcompose.presentation.Screen
import com.example.pollcompose.presentation.ui.login.LoginScreen
import com.example.pollcompose.presentation.ui.login.LoginViewModel
import com.example.pollcompose.presentation.ui.splash.SplashScreen
import com.example.pollcompose.presentation.ui.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.SplashScreen.route){
                composable(Screen.SplashScreen.route){ navBackStackEntry ->
                    val factory = HiltViewModelFactory(LocalContext.current,navBackStackEntry)
                    val viewModel : SplashViewModel = viewModel(key = "SplashViewModel",factory = factory)
                    SplashScreen(viewModel = viewModel, navigatePage = navController::navigate)
                }
                composable(Screen.LoginScreen.route){ navBackStackEntry ->
                    val factory = HiltViewModelFactory(LocalContext.current,navBackStackEntry)
                    val viewModel : LoginViewModel = viewModel(key = "LoginViewModel",factory = factory)
                    LoginScreen(viewModel = viewModel, navigatePage = navController::navigate)
                }
            }
        }
    }
}