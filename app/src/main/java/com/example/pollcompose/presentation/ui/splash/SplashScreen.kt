package com.example.pollcompose.presentation.ui.splash

import android.annotation.SuppressLint
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pollcompose.R
import com.example.pollcompose.presentation.Screen
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    navigatePage : (String) -> Unit
){

    val loading = viewModel.loading
    val token = viewModel.token
    val authenticatedId = viewModel.authenticatedId

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(.8f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.size(100.dp),
                    alignment = Alignment.Center,
                    painter = painterResource(id = R.drawable.ic_poll),
                    contentDescription = "Logo"
                )
            }
            Box(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(.2f),
                contentAlignment = Alignment.Center
            ) {
                if (loading.value)
                    CircularProgressIndicator(
                        color = Color.Black
                    )
            }

        }
    }

    token.value?.let{ tokenValue->
        if(tokenValue.isNotEmpty()){
            viewModel.authenticateToken(tokenValue)
        }else{
            CoroutineScope(Dispatchers.Main).launch{
                delay(1000)
                navigatePage(Screen.LoginScreen.route)
            }
        }
    }

    authenticatedId.value?.let { id->
        if (id.isNotEmpty())
            navigatePage(Screen.MainScreen(id).route)
        else
            navigatePage(Screen.LoginScreen.route)
    }
}