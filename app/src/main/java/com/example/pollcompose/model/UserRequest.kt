package com.example.pollcompose.model

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val id : String,
    val mail : String,
    val name : String,
    val token : String,
    val imageUrl : String? = null
){
    fun toRequestDTO() = UserRequestDTO(
        id, mail, name, imageUrl
    )
}

@Serializable
data class UserRequestDTO(
    val id : String,
    val mail : String,
    val name : String,
    val imageUrl : String? = null
)
