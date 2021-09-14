package com.example.pollcompose.presentation.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pollcompose.R
import com.example.pollcompose.domain.model.AccountRequest
import com.example.pollcompose.presentation.Screen
import com.example.pollcompose.presentation.theme.Brown
import com.example.pollcompose.presentation.theme.GreenLight
import com.example.pollcompose.presentation.ui.components.common.AppTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalUnitApi
@ExperimentalCoroutinesApi
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigatePage : (String) -> Unit
){

    val loading = viewModel.loading
    val authenticatedId = viewModel.authenticatedId
    val dialogQueue = viewModel.queue

    fun login(){
        val email = viewModel.email.value
        val password = viewModel.password.value
        viewModel.login( AccountRequest(email, password) )
    }

    authenticatedId.value?.let { id->
        if (id.isNotEmpty()){
            navigatePage(Screen.MainScreen(id).route)
        }
    }

    AppTheme(
        dialogQueue = dialogQueue.queue.value,
        dialogDismiss = {dialogQueue.removeDialog()}
    ) {
        Scaffold {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .padding(10.dp)
                            .size(36.dp),
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
                var passwordVisibility = remember { mutableStateOf(false) }
                TextField(
                    value = viewModel.password.value,
                    onValueChange = { entry->
                        viewModel.setPassword(entry)
                    },
                    visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                        else PasswordVisualTransformation(),
                    label = { Text(LocalContext.current.getString(R.string.password)) },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White
                    ),
                    modifier = Modifier
                        .padding(bottom = 40.dp)
                        .fillMaxWidth(),
                    trailingIcon = {
                        val image = if (passwordVisibility.value)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        IconButton(onClick = {
                            passwordVisibility.value = !passwordVisibility.value
                        }) {
                            Icon(imageVector  = image, "")
                        }
                    }

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
}