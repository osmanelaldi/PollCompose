package com.example.pollcompose.model

import kotlinx.serialization.Serializable
import java.util.*
import kotlin.collections.ArrayList

@Serializable
data class Option(
    val optionId : String,
    val description : String,
    val pollId : String,
    val votecount : Int,
    var votes : ArrayList<Vote>?
)

@Serializable
data class OptionDTO(
    val optionId : String,
    val description : String,
    val pollId : String
)

data class CreateOption(
    val optionId : String = UUID.randomUUID().toString(),
    var description : String = ""
)