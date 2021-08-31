package com.example.pollcompose.data.datasource

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppDataSource
@Inject
constructor(app : Application){

    private val dataSource : DataStore<Preferences> = app.createDataStore("account")

    private val scope = CoroutineScope(Main)

    init {
        observeDataStore()
    }

    val accountId = mutableStateOf("")

    fun saveUserId(userId : String){
        scope.launch {
            dataSource.edit { preferences->
                preferences[ACCOUNT_ID] = userId
            }
        }
    }

    fun observeDataStore(){
        dataSource.data.onEach { preferences->
            preferences[ACCOUNT_ID]?.let { id->
                accountId.value = id
            }
        }
    }

    companion object{
        private val ACCOUNT_ID = stringPreferencesKey("account_id_key")
    }
}