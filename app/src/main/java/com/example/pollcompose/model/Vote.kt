package com.example.pollcompose.domain.model

data class Vote(
    val description: String,
    val pollId: String,
    val userId: String,
    val voteId: String
)