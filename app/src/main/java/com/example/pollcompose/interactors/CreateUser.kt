package com.example.pollcompose.interactors

import com.example.pollcompose.data.network.PollService
import com.example.pollcompose.domain.model.util.DataState
import com.example.pollcompose.model.SignupResponse
import com.example.pollcompose.model.UserRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class CreateUser(
    private val pollService: PollService,
){
    fun execute(userRequest: UserRequest) : Flow<DataState<Boolean>> = flow {
        try {
            emit(DataState.loading<Boolean>())
            pollService.createUser(userRequest)
            emit(DataState.data(data = true))
        }catch (e : Exception){
            emit(
                DataState.error<Boolean>(message = e.message ?: "UnknownError")
            )
        }
    }
}