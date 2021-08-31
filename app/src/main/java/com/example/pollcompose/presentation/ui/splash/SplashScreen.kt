package com.example.pollcompose.presentation.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.pollcompose.R
import com.example.pollcompose.presentation.Screen
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun SplashScreen(
    token : String = "",
    viewModel: SplashViewModel,
    navigatePage : (String) -> Unit
){

    val loading = viewModel.loading
    val authenticatedId = viewModel.authenticatedId

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(.5f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painterResource(id = R.drawable.ic_poll),
                contentDescription = "Logo"
            )
            if (loading.value)
                CircularProgressIndicator()
        }
    }

    if(token.isNotEmpty()){
        viewModel.authenticateToken(token)
    }else{
        navigatePage(Screen.LoginScreen.route)
    }

    authenticatedId.value?.let { id->
        if (id.isNotEmpty())
            navigatePage(Screen.MainScreen.route)
        else
            navigatePage(Screen.LoginScreen.route)
    }
}