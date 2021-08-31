package com.example.pollcompose.data.network

import com.example.pollcompose.domain.model.AccountRequest
import com.example.pollcompose.domain.model.AccountResponse
import com.example.pollcompose.domain.model.Poll

interface PollService {


   suspend fun signUp(accountRequest: AccountRequest) : AccountResponse

   suspend fun login(accountRequest: AccountRequest) : AccountResponse

   suspend fun authToken(token : String) : AccountResponse

   suspend fun getPolls() : List<Poll>
}