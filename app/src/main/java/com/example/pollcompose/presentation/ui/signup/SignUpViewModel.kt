package com.example.pollcompose.presentation.ui.signup

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pollcompose.domain.model.AccountRequest
import com.example.pollcompose.interactors.CreateUser
import com.example.pollcompose.interactors.SignUp
import com.example.pollcompose.model.SignupResponse
import com.example.pollcompose.presentation.ui.components.DialogQueue
import com.example.pollcompose.presentation.ui.components.MessageDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SignUpViewModel
@Inject
constructor(
    private val signUp : SignUp,
    private val createUser: CreateUser,
    private val state : SavedStateHandle
) : ViewModel(){

    val newUser : MutableState<SignupResponse?> = mutableStateOf(null)
    val loading = mutableStateOf(false)
    val emailMatches = mutableStateOf(false)
    val passwordMatches = mutableStateOf(false)
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val name = mutableStateOf("")
    val queue = DialogQueue()
    init {
        state.get<String>(SIGNUP_EMAIL_KEY)?.let { email->
            setEmail(email)
        }
        state.get<String>(SIGNUP_PASSWORD_KEY)?.let { password->
            setPassword(password)
        }
    }

    fun signUp(accountRequest: AccountRequest){
        viewModelScope.launch {
            try {
                executeSignUp(accountRequest = accountRequest)
            }catch (e: Exception){
                Log.e("DEBUG_ERROR", "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
        }
    }

    private fun executeSignUp(accountRequest: AccountRequest){
        signUp.execute(accountRequest, emailMatches.value && passwordMatches.value).onEach { dataState->
            loading.value = dataState.isLoading

            dataState.data?.let { data->
                newUser.value = data
            }
            dataState.error?.let { errorMessage->
                queue.appendDialog(MessageDialog.Error(errorMessage))
            }

        }.launchIn(viewModelScope)
    }

    fun setEmail(emailEntry : String){
        state.set(com.example.pollcompose.presentation.ui.login.LOGIN_EMAIL_KEY, email)
        email.value = emailEntry
        checkEmailAndPassword()
    }

    fun setPassword(passwordEntry : String){
        state.set(com.example.pollcompose.presentation.ui.login.LOGIN_PASSWORD_KEY, password)
        password.value = passwordEntry
        checkEmailAndPassword()
    }

    private fun checkEmailAndPassword(){
        emailMatches.value = emailRegex.find(email.value) != null
        passwordMatches.value = password.value.length >= 6
    }

    private val emailRegex = ".+@.+\\.[a-z]+".toRegex()

}



const val SIGNUP_EMAIL_KEY = "SIGNUP_EMAIL_KEY"
const val SIGNUP_PASSWORD_KEY = "SIGNUP_PASSWORD_KEY"
const val SIGNUP_NAME_KEY = "SIGNUP_NAME_KEY"