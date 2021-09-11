package com.example.pollcompose.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class AccountRequest(
    val email : String,
    val password : String,
    val name : String = ""
){
   fun toRequestDTO() = AccountRequestDTO(email, password)
}

@Serializable
data class AccountRequestDTO(
    val email : String,
    val password : String
)