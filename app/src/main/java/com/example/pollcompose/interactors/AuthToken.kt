package com.example.pollcompose.interactors

import com.example.pollcompose.data.network.PollService
import com.example.pollcompose.domain.model.util.DataState
import com.example.pollcompose.model.AccountResponse
import com.example.pollcompose.model.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthToken(
    private val pollService: PollService
){
    fun execute(token : String): Flow<DataState<UserResponse>> = flow {
        try {
            emit(DataState.loading<UserResponse>())
            val response = pollService.authToken(token = token)
            emit(DataState.data(data = response))
        }catch (e: Exception){
            emit(
                DataState.error<UserResponse>(
                message = e.message ?: "Unknown Error"
            ))
        }
    }
}