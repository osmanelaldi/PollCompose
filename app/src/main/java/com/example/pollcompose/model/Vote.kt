package com.example.pollcompose.model

import kotlinx.serialization.Serializable

@Serializable
data class Vote(
    val optionId : String,
    val pollId: String,
    val userId: String,
    val voteId: String
)