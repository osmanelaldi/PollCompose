package com.example.pollcompose.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class AccountRequest(
    val email : String,
    val password : String,
    val name : String = ""
)
