package com.example.pollcompose.presentation.ui.login

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pollcompose.domain.model.AccountRequest
import com.example.pollcompose.interactors.Login
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val login: Login,
    val state : SavedStateHandle
    ) : ViewModel(){

    val authenticatedId : MutableState<String?> = mutableStateOf(null)
    val loading = mutableStateOf(false)
    val email = mutableStateOf("")
    val password = mutableStateOf("")

    init {
        state.get<String>(LOGIN_EMAIL_KEY)?.let { email->
            setEmail(email)
        }
        state.get<String>(LOGIN_PASSWORD_KEY)?.let { password->
            setPassword(password)
        }
    }

    fun login(accountRequest: AccountRequest){
        viewModelScope.launch {
            try {
                executeLogin(accountRequest = accountRequest)
            }catch (e: Exception){
                Log.e("DEBUG_ERROR", "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
        }
    }

    private fun executeLogin(accountRequest: AccountRequest){
        login.execute(accountRequest).onEach { dataState->
            loading.value = dataState.isLoading

            dataState.data?.let { data->
                authenticatedId.value = data.id
            }
            dataState.error?.let {
                authenticatedId.value = ""
            }

        }.launchIn(viewModelScope)
    }

    fun setEmail(emailEntry : String){
        state.set(LOGIN_EMAIL_KEY, email)
        email.value = emailEntry
    }

    fun setPassword(passwordEntry : String){
        state.set(LOGIN_PASSWORD_KEY, password)
        password.value = passwordEntry
    }
}