package com.example.pollcompose.presentation.ui.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pollcompose.data.datasource.AppDataSource
import com.example.pollcompose.interactors.GetPolls
import com.example.pollcompose.interactors.GetUser
import com.example.pollcompose.interactors.PollVote
import com.example.pollcompose.model.Poll
import com.example.pollcompose.model.User
import com.example.pollcompose.model.Vote
import com.example.pollcompose.presentation.ui.components.DialogQueue
import com.example.pollcompose.presentation.ui.components.MessageDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val getPolls : GetPolls,
    private val getUser : GetUser,
    private val pollVote: PollVote,
    private val appDataSource: AppDataSource
) : ViewModel(){

    val loading : MutableState<Boolean> = mutableStateOf(false)
    val user : MutableState<User?> = mutableStateOf(null)
    val polls : MutableState<ArrayList<Poll>> = mutableStateOf<ArrayList<Poll>>(arrayListOf())
    val dialogQueue = DialogQueue()

    init {
        appDataSource.bindDataSource(viewModelScope)
    }

    fun getUser(userId : String){
        viewModelScope.launch {
            try {
                getUser.execute(userId, appDataSource.token.value!!).onEach { dataState ->
                    loading.value = dataState.isLoading

                    dataState.data?.let { userValue->
                        user.value = userValue
                        appDataSource.saveUser(userValue)
                        getPolls()
                    }
                    dataState.error?.let { errorMessage->
                        dialogQueue.appendDialog(MessageDialog.Error(errorMessage))
                    }
                }.launchIn(viewModelScope)
            }catch (e : Exception){
                e.printStackTrace()
            }
        }
    }

    private fun getPolls(){
        viewModelScope.launch {
            try {
                getPolls.executeGetPolls(appDataSource.token.value!!).onEach { dataState ->
                    loading.value = dataState.isLoading

                    dataState.data?.let { pollItems->
                        polls.value = pollItems as ArrayList<Poll>
                    }
                    dataState.error?.let { errorMessage->
                        dialogQueue.appendDialog(MessageDialog.Error(errorMessage))
                    }
                }.launchIn(viewModelScope)
            }catch (e : Exception){
                e.printStackTrace()
            }
        }
    }

    fun vote(vote : Vote){
        viewModelScope.launch {
            try {
                pollVote.execute(vote = vote, token = appDataSource.token.value!!).onEach { dataState ->
                    loading.value = dataState.isLoading

                    dataState.data?.let {
                        getPollWithId(vote.pollId)
                    }

                    dataState.error?.let { errorMessage->
                        dialogQueue.appendDialog(MessageDialog.Error(errorMessage))
                    }
                }.launchIn(viewModelScope)
            }catch (e : Exception){
                e.printStackTrace()
            }
        }
    }

    private fun getPollWithId(pollId : String){
        viewModelScope.launch {
            try {
                getPolls.executeGetPollsWithId(pollId,appDataSource.token.value!!).onEach { dataState ->
                    loading.value = dataState.isLoading

                    dataState.data?.let { pollItem->
                        val index = polls.value.indexOfFirst { it.pollId == pollItem.pollId }
                        polls.value[index] = pollItem
                        val newValues = polls.value.toMutableList()
                        polls.value = arrayListOf()
                        polls.value = ArrayList(newValues)
                    }
                    dataState.error?.let { errorMessage->
                        dialogQueue.appendDialog(MessageDialog.Error(errorMessage))
                    }
                }.launchIn(viewModelScope)
            }catch (e : Exception){
                e.printStackTrace()
            }
        }
    }
}