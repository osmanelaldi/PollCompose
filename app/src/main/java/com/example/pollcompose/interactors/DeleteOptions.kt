package com.example.pollcompose.interactors

import com.example.pollcompose.data.network.PollService
import com.example.pollcompose.domain.model.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class DeleteOptions (
    private val pollService: PollService,
){
    fun execute(pollId : String, token : String) : Flow<DataState<Any>> = flow {
        try {
            emit(DataState.loading<Any>())
            val response = pollService.deleteOptions(pollId, token)
            emit(DataState.data(data = response))
        }catch (e : Exception){
            emit(
                DataState.error<Any>(message = e.message ?: "UnknownError")
            )
        }
    }
}