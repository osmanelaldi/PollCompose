package com.example.pollcompose.interactors

import com.example.pollcompose.data.network.PollService
import com.example.pollcompose.model.Poll
import com.example.pollcompose.domain.model.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.NullPointerException

class GetPolls(
    private val pollService : PollService
) {
    fun executeGetPolls(token : String) : Flow<DataState<List<Poll>?>> = flow {
        try {
            emit(DataState.loading<List<Poll>?>())
            val polls = pollService.getPolls(token)
            emit(DataState.data<List<Poll>?>(data = polls))

        }catch (e: Exception){
            if (e !is NullPointerException)
                emit(DataState.error<List<Poll>?>(
                    message = e.message ?: "Unknown Error"
                ))
        }
    }

    fun executeGetPollsWithId(pollId : String, token: String) : Flow<DataState<Poll>> = flow {
        try {
            emit(DataState.loading<Poll>())
            val polls = pollService.getPollWithId(pollId, token)
            emit(DataState.data(data = polls.first()))

        }catch (e: Exception){
            emit(DataState.error<Poll>(
                message = e.message ?: "Unknown Error"
            ))
        }
    }

}