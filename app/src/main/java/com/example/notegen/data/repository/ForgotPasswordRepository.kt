package com.example.notegen.data.repository

import com.example.notegen.data.model.ForgotPasswordRequest
import com.example.notegen.data.model.ForgotPasswordResponse
import com.example.notegen.data.remote.api.AuthApi
import retrofit2.Response   // ✅ CORRECT IMPORT

class ForgotPasswordRepository(
    private val authApi: AuthApi
) {

    suspend fun forgotPassword(
        email: String,
        token: String
    ): Response<ForgotPasswordResponse> {

        val request = ForgotPasswordRequest(
            email = email
        )

        return authApi.forgotPassword(
            token = token,
            body = request
        )
    }
}
