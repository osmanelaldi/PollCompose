package com.example.pollcompose.interactors

import com.example.pollcompose.data.network.PollService
import com.example.pollcompose.domain.model.util.DataState
import com.example.pollcompose.model.Poll
import com.example.pollcompose.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUser(
    private val pollService : PollService
) {
    fun execute(userId : String, token : String) : Flow<DataState<User>> = flow {
        try {
            emit(DataState.loading<User>())
            val users = pollService.getUser(userId,token)
            emit(DataState.data(data = users.first()))

        }catch (e: Exception){
            emit(
                DataState.error<User>(
                message = e.message ?: "Unknown Error"
            ))
        }
    }
}