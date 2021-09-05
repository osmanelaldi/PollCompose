package com.example.pollcompose.interactors

import android.content.Context
import com.example.pollcompose.R
import com.example.pollcompose.data.network.PollService
import com.example.pollcompose.domain.model.AccountRequest
import com.example.pollcompose.domain.model.util.DataState
import com.example.pollcompose.model.AccountResponse
import com.example.pollcompose.model.SignupResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SignUp(
    private val pollService: PollService,
    private val context : Context
){
    fun execute(accountRequest: AccountRequest, isMatches : Boolean): Flow<DataState<SignupResponse>> = flow {
        try {
            val isNotEmpty = accountRequest.email.isNotEmpty() && accountRequest.password.isNotEmpty() && accountRequest.name.isNotEmpty()
            if (isNotEmpty && isMatches){
                emit(DataState.loading<SignupResponse>())
                val response = pollService.signUp(accountRequest = accountRequest)
                emit(DataState.data(data = SignupResponse(accountResponse = response,userName = accountRequest.name)))
            }else if(!isNotEmpty){
                emit(DataState.error<SignupResponse>(
                    message = context.getString(R.string.email_or_password_empty)
                ))
            }else if (!isMatches){
                emit(DataState.error<SignupResponse>(
                    message = context.getString(R.string.please_fill_all_fields)
                ))
            }
        }catch (e: Exception){
            emit(DataState.error<SignupResponse>(
                message = e.message ?: "Unknown Error"
            ))
        }
    }
}