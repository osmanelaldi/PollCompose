package com.example.pollcompose.presentation.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.padding(10.dp).size(100.dp),
                painter = painterResource(id = R.drawable.ic_poll),
                contentDescription = "Logo"
            )
            if (loading.value)
                CircularProgressIndicator(
                    color = Color.Black
                )
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