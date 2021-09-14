package com.example.pollcompose.data.network

import com.example.pollcompose.domain.model.AccountRequestDTO
import com.example.pollcompose.model.*

interface PollService {

   suspend fun signUp(accountRequest: AccountRequestDTO) : AccountResponse

   suspend fun login(accountRequest: AccountRequestDTO) : AccountResponse

   suspend fun authToken(token : String) : UserResponse

   suspend fun createUser(userRequest: UserRequest) : Any

   suspend fun getUser(userId : String, token : String) : List<User>

   suspend fun getPolls(token: String) : List<Poll>?

   suspend fun getPollWithId(pollId : String, token: String) : List<Poll>

   suspend fun vote(vote : Vote, token : String) : Any

   suspend fun createPoll(pollDTO: PollDTO, token: String) : Any

   suspend fun createOptions(options : List<OptionDTO>, token: String) : Any

   suspend fun deleteVotes(pollId: String, token: String) : Any

   suspend fun deleteOptions(pollId: String, token: String) : Any

   suspend fun deletePoll(pollId: String, token: String) : Any
}