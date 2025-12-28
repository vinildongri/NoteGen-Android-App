package com.example.notegen.data.model

import com.example.notegen.viewModels.LoginState

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val user: UserDto
)