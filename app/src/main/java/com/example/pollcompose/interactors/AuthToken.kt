package com.example.pollcompose.interactors

import com.example.pollcompose.data.network.PollService
import com.example.pollcompose.domain.model.util.DataState
import com.example.pollcompose.model.AccountResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthToken(
    private val pollService: PollService
){
    fun execute(token : String): Flow<DataState<AccountResponse>> = flow {
        try {
            emit(DataState.loading<AccountResponse>())
            val response = pollService.authToken(token = token)
            emit(DataState.data(data = response))
        }catch (e: Exception){
            emit(
                DataState.error<AccountResponse>(
                message = e.message ?: "Unknown Error"
            ))
        }
    }
}