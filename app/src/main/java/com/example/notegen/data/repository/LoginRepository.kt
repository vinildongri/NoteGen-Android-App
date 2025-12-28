package com.example.notegen.data.repository

import com.example.notegen.data.model.LoginRequest
import com.example.notegen.data.model.LoginResponse
import com.example.notegen.data.remote.api.AuthApi
import retrofit2.Response

class LoginRepository(
    private val api: AuthApi
) {
    suspend fun login(
        email: String,
        password: String
    ): Response<LoginResponse> {
        return api.login(LoginRequest(email, password))
    }
}
