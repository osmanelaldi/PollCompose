package com.example.pollcompose.interactors

import android.content.Context
import com.example.pollcompose.R
import com.example.pollcompose.data.network.PollService
import com.example.pollcompose.domain.model.AccountRequest
import com.example.pollcompose.domain.model.util.DataState
import com.example.pollcompose.model.AccountResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Login (
    private val pollService: PollService,
    private val context : Context
){
    fun execute(accountRequest: AccountRequest): Flow<DataState<AccountResponse>> = flow {
        try {
            if (accountRequest.email.isNotEmpty() && accountRequest.password.isNotEmpty()) {
                emit(DataState.loading<AccountResponse>())
                val response = pollService.login(accountRequest = accountRequest)
                emit(DataState.data(data = response))
            }else{
                emit(DataState.error<AccountResponse>(
                    message = context.getString(R.string.email_or_password_empty)
                ))
            }
        }catch (e: Exception){
            emit(
                DataState.error<AccountResponse>(
                message = e.message ?: "Unknown Error"
            ))
        }
    }
}