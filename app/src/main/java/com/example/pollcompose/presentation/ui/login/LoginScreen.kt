package com.example.pollcompose.presentation.ui.login

import android.graphics.fonts.FontStyle
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.*
import com.example.pollcompose.R
import com.example.pollcompose.domain.model.AccountRequest
import com.example.pollcompose.presentation.Screen
import com.example.pollcompose.presentation.theme.Brown
import com.example.pollcompose.presentation.theme.GreenLight
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalUnitApi
@ExperimentalCoroutinesApi
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    navigatePage : (String) -> Unit
){

    val loading = viewModel.loading
    val authenticatedId = viewModel.authenticatedId

    fun login(){
        val email = viewModel.email.value
        val password = viewModel.password.value
        if (email.isNotEmpty() && password.isNotEmpty())
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
                .padding(50.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.padding(10.dp).size(36.dp),
                    painter = painterResource(id = R.drawable.ic_poll),
                    contentDescription = "Logo"
                )
                Text(
                    text = LocalContext.current.getString(R.string.app_name),
                    fontSize = TextUnit(22f, TextUnitType.Sp),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Brown
                )
            }
            TextField(
                value = viewModel.email.value,
                onValueChange = { entry->
                    viewModel.setEmail(entry)
                },
                label = { Text(LocalContext.current.getString(R.string.email)) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                ),
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 15.dp)
                    .fillMaxWidth()
            )
            TextField(
                value = viewModel.password.value,
                onValueChange = { entry->
                    viewModel.setPassword(entry)
                },
                label = { Text(LocalContext.current.getString(R.string.password)) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                ),
                modifier = Modifier
                    .padding(bottom = 40.dp)
                    .fillMaxWidth()

            )
            Button(
                shape = RoundedCornerShape(5),
                colors = ButtonDefaults.buttonColors(backgroundColor = GreenLight),
                modifier = Modifier
                    .padding(bottom = 15.dp)
                    .fillMaxWidth(),
                onClick = { login() }) {
                if(loading.value)
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = Color.White
                    )
                else
                Text(
                    text = LocalContext.current.getString(R.string.login),
                    fontSize = TextUnit(13f, TextUnitType.Sp),
                    color = Color.White,
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .clickable { navigatePage(Screen.SignUpScreen.route) },
                textAlign = TextAlign.Center,
                text = LocalContext.current.getString(R.string.signup_link),
                fontSize = TextUnit(13f, TextUnitType.Sp),
                style = TextStyle(
                    textDecoration = TextDecoration.combine(
                        listOf(
                            TextDecoration.Underline
                        )
                    ), fontWeight = FontWeight.Bold
                ),
                color = Color.Gray,
            )

        }
    }
}

const val LOGIN_EMAIL_KEY = "LOGIN_EMAIL_KEY"
const val LOGIN_PASSWORD_KEY = "LOGIN_PASSWORD_KEY"