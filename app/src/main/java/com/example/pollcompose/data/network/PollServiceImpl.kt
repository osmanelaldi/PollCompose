package com.example.pollcompose.data.network

import com.example.pollcompose.domain.model.AccountRequestDTO
import com.example.pollcompose.model.*
import io.ktor.client.*
import io.ktor.client.request.*

class PollServiceImpl(
    private val httpClient: HttpClient,
) : PollService {

    override suspend fun getPolls(token: String): List<Poll>? {
        return httpClient.post<List<Poll>?>{
            url("${ServiceConstants.BASE_URL}/rest/v1/rpc/getpolls")
            header(ServiceConstants.CONTENT_TYPE, ServiceConstants.CONTENT_TYPE_JSON)
            header(ServiceConstants.API_KEY, ServiceConstants.KEY)
            header(ServiceConstants.AUTHORIZATION,token)
        }
    }

    override suspend fun getPollWithId(pollId : String, token: String): List<Poll>{
        return httpClient.get<List<Poll>>{
            url("${ServiceConstants.BASE_URL}/rest/v1/rpc/getpollwithid?pollqueryid=$pollId")
            header(ServiceConstants.CONTENT_TYPE, ServiceConstants.CONTENT_TYPE_JSON)
            header(ServiceConstants.API_KEY, ServiceConstants.KEY)
            header(ServiceConstants.AUTHORIZATION,token)
        }
    }

    override suspend fun signUp(accountRequest: AccountRequestDTO): AccountResponse {
        return httpClient.post<AccountResponse>{
            url("${ServiceConstants.BASE_URL}/auth/v1/signup")
            headers{
                header(ServiceConstants.API_KEY, ServiceConstants.KEY)
                header(ServiceConstants.CONTENT_TYPE, ServiceConstants.CONTENT_TYPE_JSON)
            }
            body = accountRequest
        }
    }

    override suspend fun login(accountRequest: AccountRequestDTO): AccountResponse {
        return httpClient.post<AccountResponse>{
            url("${ServiceConstants.BASE_URL}/auth/v1/token?grant_type=password")
            headers{
                header(ServiceConstants.API_KEY, ServiceConstants.KEY)
                header(ServiceConstants.CONTENT_TYPE, ServiceConstants.CONTENT_TYPE_JSON)
            }
            body = accountRequest
        }
    }

    override suspend fun authToken(token: String): UserResponse {
        return httpClient.get<UserResponse>{
            url("${ServiceConstants.BASE_URL}/auth/v1/user")
            headers {
                header(ServiceConstants.API_KEY,ServiceConstants.KEY)
                header(ServiceConstants.AUTHORIZATION,token)
            }
        }
    }

    override suspend fun createUser(userRequest: UserRequest): Any {
        return httpClient.post<Any>("${ServiceConstants.BASE_URL}/rest/v1/User"){
            headers {
                header(ServiceConstants.API_KEY,ServiceConstants.KEY)
                header(ServiceConstants.AUTHORIZATION,userRequest.token)
                header(ServiceConstants.CONTENT_TYPE, ServiceConstants.CONTENT_TYPE_JSON)
                header(ServiceConstants.PREFER,ServiceConstants.PREFER_REPRESENTATION)
            }
            body = userRequest.toRequestDTO()
        }
    }

    override suspend fun getUser(userId : String, token : String): List<User> {
        return httpClient.get<List<User>>("${ServiceConstants.BASE_URL}/rest/v1/User?id=eq.$userId&select=*"){
            header(ServiceConstants.API_KEY,ServiceConstants.KEY)
            header(ServiceConstants.AUTHORIZATION,token)
            header(ServiceConstants.CONTENT_TYPE, ServiceConstants.CONTENT_TYPE_JSON)
        }
    }

    override suspend fun vote(vote: Vote, token : String) : Any {
        return httpClient.post<Any>("${ServiceConstants.BASE_URL}/rest/v1/Vote"){
            header(ServiceConstants.API_KEY,ServiceConstants.KEY)
            header(ServiceConstants.AUTHORIZATION,token)
            header(ServiceConstants.CONTENT_TYPE, ServiceConstants.CONTENT_TYPE_JSON)
            header(ServiceConstants.PREFER,ServiceConstants.PREFER_MERGE_DUPLICATE)
            body = vote
        }
    }

    override suspend fun createPoll(pollDTO: PollDTO, token: String): Any {
        return httpClient.post<Any>("${ServiceConstants.BASE_URL}/rest/v1/Poll"){
            header(ServiceConstants.API_KEY,ServiceConstants.KEY)
            header(ServiceConstants.AUTHORIZATION,token)
            header(ServiceConstants.CONTENT_TYPE, ServiceConstants.CONTENT_TYPE_JSON)
            header(ServiceConstants.PREFER,ServiceConstants.PREFER_MERGE_DUPLICATE)
            body = pollDTO
        }
    }

    override suspend fun createOptions(options: List<OptionDTO>, token: String): Any {
        return httpClient.post<Any>("${ServiceConstants.BASE_URL}/rest/v1/Option"){
            header(ServiceConstants.API_KEY,ServiceConstants.KEY)
            header(ServiceConstants.AUTHORIZATION,token)
            header(ServiceConstants.CONTENT_TYPE, ServiceConstants.CONTENT_TYPE_JSON)
            header(ServiceConstants.PREFER,ServiceConstants.PREFER_MERGE_DUPLICATE)
            body = options
        }
    }

    override suspend fun deleteVotes(pollId: String, token: String): Any {
        return httpClient.delete<Any>("${ServiceConstants.BASE_URL}/rest/v1/Vote?pollId=eq.$pollId"){
            header(ServiceConstants.API_KEY,ServiceConstants.KEY)
            header(ServiceConstants.AUTHORIZATION,token)
            header(ServiceConstants.CONTENT_TYPE, ServiceConstants.CONTENT_TYPE_JSON)
            header(ServiceConstants.PREFER,ServiceConstants.PREFER_MERGE_DUPLICATE)
        }
    }

    override suspend fun deleteOptions(pollId: String, token: String): Any {
        return httpClient.delete<Any>("${ServiceConstants.BASE_URL}/rest/v1/Option?pollId=eq.$pollId"){
            header(ServiceConstants.API_KEY,ServiceConstants.KEY)
            header(ServiceConstants.AUTHORIZATION,token)
            header(ServiceConstants.CONTENT_TYPE, ServiceConstants.CONTENT_TYPE_JSON)
            header(ServiceConstants.PREFER,ServiceConstants.PREFER_MERGE_DUPLICATE)
        }
    }

    override suspend fun deletePoll(pollId: String, token: String): Any {
        return httpClient.delete<Any>("${ServiceConstants.BASE_URL}/rest/v1/Poll?pollId=eq.$pollId"){
            header(ServiceConstants.API_KEY,ServiceConstants.KEY)
            header(ServiceConstants.AUTHORIZATION,token)
            header(ServiceConstants.CONTENT_TYPE, ServiceConstants.CONTENT_TYPE_JSON)
            header(ServiceConstants.PREFER,ServiceConstants.PREFER_MERGE_DUPLICATE)
        }
    }


}