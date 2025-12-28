package com.example.notegen.data.repository

import android.util.Log
import com.example.notegen.data.model.UpdateProfileRequest
import com.example.notegen.data.model.UpdateProfileResponse
import com.example.notegen.data.remote.RetrofitInstance
import com.example.notegen.utils.PreferenceManager
import retrofit2.Response

class UpdateProfileRepository (
    private val pref: PreferenceManager
){

    suspend fun updateUserProfile(
        name: String,
        email: String
    ): Response<UpdateProfileResponse>{
        val request = UpdateProfileRequest(name, email)
        val token = pref.getToken() ?: ""

        Log.d("AuthRepository", "Updating profile with token: $token")

        return RetrofitInstance.authApi.updateProfile(
            token = "token=$token",
            body = request
        )
    }

}