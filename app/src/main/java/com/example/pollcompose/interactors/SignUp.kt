package com.example.pollcompose.interactors

import com.example.pollcompose.data.network.PollService
import com.example.pollcompose.domain.model.AccountRequest
import com.example.pollcompose.domain.model.AccountResponse
import com.example.pollcompose.domain.model.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SignUp(
    private val pollService: PollService
){
    fun execute(accountRequest: AccountRequest): Flow<DataState<AccountResponse>> = flow {
        try {
            emit(DataState.loading<AccountResponse>())
            val response = pollService.signUp(accountRequest = accountRequest)
            emit(DataState.data(data = response))
        }catch (e: Exception){
            emit(DataState.error<AccountResponse>(
                message = e.message ?: "Unknown Error"
            ))
        }
    }
}