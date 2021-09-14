package com.example.pollcompose.model

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Poll(
    var description: String,
    val imageUrl: String?,
    val pollId: String,
    val user: User,
    val userId: String,
    val color : String,
    val votecount : Int,
    var options : List<Option>
){
    fun getUserVote(userId: String) : Vote?{
        options.forEach { option ->
            option.votes?.find { vote-> vote.userId ==  userId}?.let { userVote->
                return userVote
            }
        }
        return null
    }
}

@Serializable
data class PollDTO(
    val pollId: String,
    val description: String,
    val imageUrl: String? = null,
    val color : String,
    val userId: String
)

data class CreatePollItem(
    var description: String,
    var color : String,
    var createOptions: List<CreateOption>
)