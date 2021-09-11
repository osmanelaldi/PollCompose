package com.example.pollcompose.data.datasource

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.createDataStore
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.example.pollcompose.model.User
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppDataSource
@Inject
constructor(app : Application){

    private val dataSource : DataStore<Preferences> = app.createDataStore("account")

    val token : MutableState<String?> = mutableStateOf(null)
    val user : MutableState<User?> = mutableStateOf(null)

    suspend fun saveToken(token : String){
        dataSource.edit { preferences->
            preferences[TOKEN] = "Bearer $token"
        }
    }

    suspend fun saveUser(user : User){
        dataSource.edit { preferences->
            preferences[USER] = Gson().toJson(user)
        }
    }


    fun bindDataSource(scope : CoroutineScope){
        dataSource.data.onEach { preferences->
            token.value = preferences[TOKEN] ?: ""
            user.value = preferences[USER]?.let { user->
                Gson().fromJson(user,User::class.java)
            }
        }.launchIn(scope)
    }

    companion object{
        private val TOKEN = stringPreferencesKey("token")
        private val USER = stringPreferencesKey("user")
    }
}