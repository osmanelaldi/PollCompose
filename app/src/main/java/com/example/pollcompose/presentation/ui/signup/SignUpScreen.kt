package com.example.pollcompose.presentation.ui.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navigatePage : (String) -> Unit
){

    val loading = viewModel.loading
    val authenticatedId = viewModel.authenticatedId
    val emailMatches = viewModel.emailMatches
    val passwordMatches = viewModel.passwordMatches
    val dialogQueue = viewModel.queue

    fun signUp(){
        val email = viewModel.email.value
        val password = viewModel.password.value
        val name = viewModel.name.value
        viewModel.signUp( AccountRequest(email, password, name) )
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
                    value = viewModel.name.value,
                    onValueChange = { entry->
                        viewModel.setName(entry)
                    },
                    label = { Text(LocalContext.current.getString(R.string.name)) },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White
                    ),
                    modifier = Modifier
                        .padding(top = 20.dp, bottom = 15.dp)
                        .fillMaxWidth()
                )
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
                        .padding(bottom = 15.dp)
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
                    onClick = { signUp() }) {
                    if(loading.value)
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = Color.White
                        )
                    else
                        Text(
                            text = LocalContext.current.getString(R.string.signup),
                            fontSize = TextUnit(13f, TextUnitType.Sp),
                            color = Color.White,
                        )
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp),
                    textAlign = TextAlign.Start,
                    text = LocalContext.current.getString(R.string.email_format),
                    fontSize = TextUnit(12f, TextUnitType.Sp),
                    color = if(emailMatches.value) GreenLight else Color.Gray,
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp),
                    textAlign = TextAlign.Start,
                    text = LocalContext.current.getString(R.string.password_format),
                    fontSize = TextUnit(12f, TextUnitType.Sp),
                    color = if(passwordMatches.value) GreenLight else Color.Gray,
                )
            }
        }
    }
}