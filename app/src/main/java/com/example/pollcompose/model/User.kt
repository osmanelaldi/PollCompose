package com.example.pollcompose.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val imageUrl: String?,
    val name: String
)