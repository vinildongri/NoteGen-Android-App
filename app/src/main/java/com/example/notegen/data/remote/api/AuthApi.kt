package com.example.notegen.data.remote.api

import com.example.notegen.data.model.ForgotPasswordRequest
import com.example.notegen.data.model.ForgotPasswordResponse
import com.example.notegen.data.model.LoginRequest
import com.example.notegen.data.model.LoginResponse
import com.example.notegen.data.model.RegisterResponse
import com.example.notegen.data.model.UpdatePasswordRequest
import com.example.notegen.data.model.UpdatePasswordResponse
import com.example.notegen.data.model.UpdateProfileRequest
import com.example.notegen.data.model.UpdateProfileResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthApi {

//    LOGIN
    @POST("api/v1/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

//    REGISTER
    @POST("api/v1/register")
    suspend fun register(
        @Body body: Map<String, String>
    ): Response<RegisterResponse>

//    UPDATE PASSWORD
    @PUT("api/v1/password/update")
    suspend fun updatePassword(
        @Header("Cookie") token: String,
        @Body body: UpdatePasswordRequest
    ): Response<UpdatePasswordResponse>

//    UPDATE PROFILE
    @PUT("api/v1/me/update")
    suspend fun updateProfile(
        @Header("Cookie") token: String,
        @Body body: UpdateProfileRequest
    ): Response<UpdateProfileResponse>

//    FORGOT PASSWORD
    @POST("api/v1/password/forgot")
    suspend fun forgotPassword(
        @Header("Cookie") token: String,
        @Body body: ForgotPasswordRequest
    ): Response<ForgotPasswordResponse>
}
