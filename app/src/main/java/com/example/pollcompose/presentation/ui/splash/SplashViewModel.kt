package com.example.pollcompose.presentation.ui.splash

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pollcompose.data.datasource.AppDataSource
import com.example.pollcompose.interactors.AuthToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SplashViewModel
@Inject constructor(
    private val authToken: AuthToken
    ): ViewModel(){

    val authenticatedId : MutableState<String?> = mutableStateOf(null)
    val loading = mutableStateOf(false)

    fun authenticateToken(token : String){
        viewModelScope.launch {
            try {
                checkToken(token)
            }catch (e: Exception){
                Log.e("DEBUG_ERROR", "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
        }
    }

    private fun checkToken(token : String){
        authToken.execute(token).onEach { dataState->
            loading.value = dataState.isLoading

            dataState.data?.let { data->
                authenticatedId.value = data.user.id
            }
            dataState.error?.let {
                authenticatedId.value = ""
            }

        }.launchIn(viewModelScope)
    }

}