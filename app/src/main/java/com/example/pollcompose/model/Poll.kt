package com.example.pollcompose.domain.model

data class Poll(
    val description: String,
    val imageUrl: String,
    val pollId: String,
    val user: User,
    val userId: String,
    val votes: List<Vote>
)