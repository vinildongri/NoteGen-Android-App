package com.example.notegen.data.model

data class UpdatePasswordRequest(
    val oldPassword: String,
    val password: String
)
data class UpdatePasswordResponse(
    val message: String
)