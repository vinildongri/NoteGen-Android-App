package com.example.notegen.data.model

//data class RegisterModel(
//    val name: String,
//    val email: String,
//    val password: String
//)

data class User(
    val _id: String,
    val name: String,
    val email: String,
    val role: String,
    val createdAt: String,
    val updatedAt: String
)

data class RegisterResponse(
    val token: String,
    val user: User
)


