package com.example.pollcompose.model

import kotlinx.serialization.Serializable

@Serializable
data class AccountResponse(
    val access_token: String,
    val expires_in: Int,
    val refresh_token: String,
    val token_type: String,
    val user: UserResponse
)

@Serializable
data class SignupResponse(
    val accountResponse: AccountResponse,
    val userName : String
)

@Serializable
class EmptyResponse