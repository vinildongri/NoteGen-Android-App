package com.example.notegen.data.model

data class UpdateProfileRequest(
    val name: String,
    val email: String
)

data class UpdateProfileResponse(
    val message: String
)