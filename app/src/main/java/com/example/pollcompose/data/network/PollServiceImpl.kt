package com.example.pollcompose.data.network

import com.example.pollcompose.domain.model.AccountRequest
import com.example.pollcompose.domain.model.Poll
import com.example.pollcompose.model.AccountResponse
import com.example.pollcompose.model.UserRequest
import io.ktor.client.*
import io.ktor.client.request.*

class PollServiceImpl(
    private val httpClient: HttpClient,
) : PollService {

    override suspend fun getPolls(): List<Poll> {
        return httpClient.get<List<Poll>>{
            url("${ServiceConstants.BASE_URL}/rest/v1/rpc/getpolls}")
            header(ServiceConstants.API_KEY, ServiceConstants.KEY)
        }
    }

    override suspend fun signUp(accountRequest: AccountRequest): AccountResponse {
        return httpClient.post<AccountResponse>{
            url("${ServiceConstants.BASE_URL}/auth/v1/signup")
            headers{
                header(ServiceConstants.API_KEY, ServiceConstants.KEY)
                header(ServiceConstants.CONTENT_TYPE, ServiceConstants.CONTENT_TYPE_JSON)
            }
            body = accountRequest
        }
    }

    override suspend fun login(accountRequest: AccountRequest): AccountResponse {
        return httpClient.post<AccountResponse>{
            url("${ServiceConstants.BASE_URL}/auth/v1/token?grant_type=password")
            headers{
                header(ServiceConstants.API_KEY, ServiceConstants.KEY)
                header(ServiceConstants.CONTENT_TYPE, ServiceConstants.CONTENT_TYPE_JSON)
            }
            body = accountRequest
        }
    }

    override suspend fun authToken(token: String): AccountResponse {
        return httpClient.get<AccountResponse>{
            url("${ServiceConstants.BASE_URL}/auth/v1/user")
            headers {
                header(ServiceConstants.API_KEY,ServiceConstants.KEY)
                header(ServiceConstants.AUTHORIZATION,token)
            }
        }
    }

    override suspend fun createUser(userRequest: UserRequest): Void {
        return httpClient.post("${ServiceConstants.BASE_URL}/rest/v1/User"){
            headers {
                header(ServiceConstants.API_KEY,ServiceConstants.KEY)
                header(ServiceConstants.AUTHORIZATION,userRequest.token)
                header(ServiceConstants.CONTENT_TYPE, ServiceConstants.CONTENT_TYPE_JSON)
                header(ServiceConstants.PREFER,ServiceConstants.PREFER_VALUE)
            }
            body = userRequest.toRequestDTO()
        }
    }
}