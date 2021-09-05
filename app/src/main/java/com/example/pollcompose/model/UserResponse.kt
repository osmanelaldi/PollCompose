package com.example.pollcompose.model

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val aud: String,
    val confirmed_at: String,
    val created_at: String,
    val email: String,
    val email_change_confirm_status: Int,
    val email_confirmed_at: String,
    val id: String,
    val last_sign_in_at: String,
    val phone: String,
    val role: String,
    val updated_at: String
)