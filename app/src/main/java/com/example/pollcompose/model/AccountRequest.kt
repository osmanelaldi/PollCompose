package com.example.pollcompose.domain.model

import java.io.Serializable

data class AccountRequest(
    val email : String,
    val password : String
) : Serializable
