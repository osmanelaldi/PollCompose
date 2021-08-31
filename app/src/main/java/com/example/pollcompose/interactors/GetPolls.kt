package com.example.pollcompose.interactors

import com.example.pollcompose.data.network.PollService
import com.example.pollcompose.domain.model.Poll
import com.example.pollcompose.domain.model.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPolls(
    private val pollService : PollService
) {
    fun execute() : Flow<DataState<List<Poll>>> = flow {
        try {
            emit(DataState.loading<List<Poll>>())
            val polls = pollService.getPolls()
            emit(DataState.data(data = polls))

        }catch (e: Exception){
            emit(DataState.error<List<Poll>>(
                message = e.message ?: "Unknown Error"
            ))
        }
    }
}