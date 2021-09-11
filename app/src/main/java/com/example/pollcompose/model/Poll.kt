package com.example.pollcompose.model

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Poll(
    var description: String,
    val createdAt : String,
    val imageUrl: String?,
    val pollId: String,
    val user: User,
    val userId: String,
    val votecount : Int?,
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