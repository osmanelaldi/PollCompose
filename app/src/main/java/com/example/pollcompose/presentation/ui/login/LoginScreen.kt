package com.example.pollcompose.presentation.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import com.example.pollcompose.R
import com.example.pollcompose.domain.model.AccountRequest
import com.example.pollcompose.presentation.Screen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    navigatePage : (String) -> Unit
){

    val loading = viewModel.loading
    val authenticatedId = viewModel.authenticatedId

    fun login(){
        val email = viewModel.state.get<String>(LOGIN_EMAIL_KEY)
        val password = viewModel.state.get<String>(LOGIN_PASSWORD_KEY)
        if (email != null && password !=null)
            viewModel.login(
                AccountRequest(email, password)
            )
    }

    authenticatedId.value?.let { id->
        if (id.isNotEmpty()){
            navigatePage(Screen.MainScreen.route)
        }
    }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dp(40f))
        ) {
            Text(
                text = LocalContext.current.getString(R.string.login),
                style = MaterialTheme.typography.h2,
                color = Color.Black
            )
            TextField(
                value = viewModel.state.get<String>(LOGIN_EMAIL_KEY) ?: "",
                onValueChange = { email->
                    viewModel.state.set(LOGIN_EMAIL_KEY, email)
                },
                modifier = Modifier.padding(top = Dp(30f), bottom = Dp(15f))
            )
            TextField(
                value = viewModel.state.get<String>(LOGIN_PASSWORD_KEY) ?: "",
                onValueChange = { password->
                    viewModel.state.set(LOGIN_PASSWORD_KEY, password)
                },
                Modifier.padding(bottom = Dp(15f))
            )
            Button(
                shape = RoundedCornerShape(5),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
                modifier = Modifier.padding(bottom = Dp(15f)),
                onClick = { login() }) {
                Text(
                    text = LocalContext.current.getString(R.string.login),
                    style = MaterialTheme.typography.h5,
                    color = Color.White,
                )
            }
            Button(
                shape = RoundedCornerShape(5),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                modifier = Modifier.padding(bottom = Dp(15f)),
                onClick = { navigatePage(Screen.SignUpScreen.route) }) {
                Text(
                    text = LocalContext.current.getString(R.string.signup_link),
                    style = MaterialTheme.typography.h5,
                    color = Color.Black,
                )
            }

        }
    }
}

const val LOGIN_EMAIL_KEY = "LOGIN_EMAIL_KEY"
const val LOGIN_PASSWORD_KEY = "LOGIN_PASSWORD_KEY"