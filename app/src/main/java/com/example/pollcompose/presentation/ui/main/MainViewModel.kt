package com.example.pollcompose.presentation.ui.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pollcompose.data.datasource.AppDataSource
import com.example.pollcompose.interactors.*
import com.example.pollcompose.model.*
import com.example.pollcompose.presentation.ui.components.DialogQueue
import com.example.pollcompose.presentation.ui.components.MessageDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val getPolls : GetPolls,
    private val getUser : GetUser,
    private val pollVote: PollVote,
    private val createPoll : CreatePoll,
    private val createOptions: CreateOptions,
    private val deleteVotes: DeleteVotes,
    private val deleteOptions: DeleteOptions,
    private val deletePoll : DeletePoll,
    private val appDataSource: AppDataSource
) : ViewModel(){

    val createVisibility : MutableState<Boolean> = mutableStateOf(false)
    val loading : MutableState<Boolean> = mutableStateOf(false)
    val user : MutableState<User?> = mutableStateOf(null)
    val polls : MutableState<List<Poll>> = mutableStateOf<List<Poll>>(listOf())
    val dialogQueue = DialogQueue()

    init {
        appDataSource.bindDataSource(viewModelScope)
    }

    private fun reloadPolls(updatePolls : List<Poll>){
        polls.value = listOf<Poll>()
        polls.value = updatePolls
    }

    private fun List<Poll>.updatePoll(poll: Poll) : List<Poll>{
        val update = this.find { it.pollId == poll.pollId}
        return if (update != null) {
            update.options = poll.options
            this
        } else{
            val temp = ArrayList(this)
            temp.add(poll)
            temp
        }
    }

    private fun List<Poll>.removePoll(poll: Poll) : List<Poll>{
        val temp = ArrayList(this)
        temp.remove(poll)
        return temp
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
                        polls.value = pollItems
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
                        reloadPolls(polls.value.updatePoll(pollItem))
                        createVisibility.value = false
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

    fun createPoll(createPollItem: CreatePollItem){
        val pollDTO = PollDTO(
            pollId = UUID.randomUUID().toString(),
            description = createPollItem.description,
            imageUrl = null,
            color = createPollItem.color,
            userId = user.value!!.id
        )
        val options = createPollItem.createOptions.map { createOption ->
            OptionDTO(
                optionId = createOption.optionId,
                description = createOption.description,
                pollDTO.pollId
            )
        }
            viewModelScope.launch {
                try {
                    createPoll.execute(pollDTO,appDataSource.token.value!!).onEach { dataState->
                        loading.value = dataState.isLoading

                        dataState.data?.let {
                            createOptions(options)
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

    private fun createOptions(options : List<OptionDTO>){
        try {
            createOptions.execute(options,appDataSource.token.value!!).onEach { dataState ->
                loading.value = dataState.isLoading

                dataState.data?.let {
                    getPollWithId(options.first().pollId)
                }
                dataState.error?.let { errorMessage->
                    dialogQueue.appendDialog(MessageDialog.Error(errorMessage))
                }
            }.launchIn(viewModelScope)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun deletePoll(poll: Poll){
        deleteVotes(poll)
    }

    private fun deleteVotes(poll: Poll){
        viewModelScope.launch {
            try {
                deleteVotes.execute(poll.pollId, appDataSource.token.value!!).onEach { dataState->
                    loading.value = dataState.isLoading

                    dataState.data?.let {
                        deleteOptions(poll)
                    }
                    dataState.error?.let { errorMessage->
                        dialogQueue.appendDialog(MessageDialog.Error(errorMessage))
                    }
                }.launchIn(viewModelScope)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun deleteOptions(poll: Poll){
        try {
            deleteOptions.execute(poll.pollId, appDataSource.token.value!!).onEach { dataState->
                loading.value = dataState.isLoading

                dataState.data?.let {
                    deletePollDTO(poll)
                }
                dataState.error?.let { errorMessage->
                    dialogQueue.appendDialog(MessageDialog.Error(errorMessage))
                }
            }.launchIn(viewModelScope)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun deletePollDTO(poll: Poll){
        try {
            deletePoll.execute(poll.pollId, appDataSource.token.value!!).onEach { dataState->
                loading.value = dataState.isLoading

                dataState.data?.let {
                    reloadPolls(polls.value.removePoll(poll))
                }
                dataState.error?.let { errorMessage->
                    dialogQueue.appendDialog(MessageDialog.Error(errorMessage))
                }
            }.launchIn(viewModelScope)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}