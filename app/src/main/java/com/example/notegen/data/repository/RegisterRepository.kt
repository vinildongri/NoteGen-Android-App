package com.example.notegen.data.repository

import com.example.notegen.data.model.RegisterResponse
import com.example.notegen.data.remote.api.AuthApi

class RegisterRepository(
    private val api: AuthApi
) {

    suspend fun register(
        name: String,
        email: String,
        password: String
    ): RegisterResponse {

        val response = api.register(
            mapOf(
                "name" to name,
                "email" to email,
                "password" to password
            )
        )

        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            throw Exception(response.errorBody()?.string() ?: "Register failed")
        }
    }
}
