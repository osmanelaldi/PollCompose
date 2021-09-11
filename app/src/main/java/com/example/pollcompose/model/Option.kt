package com.example.pollcompose.model

import kotlinx.serialization.Serializable

@Serializable
data class Option(
    val optionId : String,
    val description : String,
    val pollId : String,
    val votecount : Int?,
    var votes : ArrayList<Vote>?
)